import * as admin from "firebase-admin";
import * as functions from "firebase-functions";

import { configureUser } from "./configureUser";

export const configureUserUpdate = functions.firestore
    .document("users/{userId}")
    .onUpdate(async (change, context) => {
        const userId = context.params.userId;
        const user = change.after.data();
        const prevUser = change.before.data();

        const isPrimaryDataChanged = user.name !== prevUser.name || user.surname !== prevUser.surname;
        const isPrivateChanged = user.private !== prevUser.private;

        if (!isPrivateChanged && user.private) return null;
        if (!isPrimaryDataChanged && !isPrivateChanged) return null;

        console.log(`Detected changes on user ${userId}.`);

        let promises: Promise<any>[] = [];
        promises.push(configureUser(user, change.after.ref));

        // Change profile picture metadata property `private` to match `user.private`
        if(isPrivateChanged) {
            let promise = admin
                .storage()
                .bucket()
                .file(`users/${userId}.png`)
                .setMetadata({
                    metadata: {
                        private: user.private.toString()
                    }
                });

            promises.push(promise);
        }

        return Promise.all(promises).catch(e => {
            if(e.code === 404) return;
            console.log(`An error occured: ${e}`);
        });
    });