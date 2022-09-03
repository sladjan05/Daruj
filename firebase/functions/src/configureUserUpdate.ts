import * as admin from "firebase-admin";
import * as functions from "firebase-functions";

export const configureUserUpdate = functions.firestore
    .document("users/{userId}")
    .onUpdate(async (change, context) => {
        if(change.after.isEqual(change.before)) return null;

        const userId = context.params.userId;
        const user = change.after.data();
        const prevUser = change.before.data();
        const userRef = change.after.ref;

        const isPrimaryDataChanged = user.name !== prevUser.name || user.surname !== prevUser.surname;
        const isPrivateChanged = user.private !== prevUser.private;

        if (!isPrivateChanged && user.private) return null;
        if (!isPrimaryDataChanged && !isPrivateChanged) return null;

        console.log(`Detected changes on user ${userId}.`);

        let promises: Promise<any>[] = [];

        // Display Name
        promises.push((async () => {
            let displayName: string;

            if (user.private) {
                displayName = `Korisnik ${userRef.id.substring(0, 5).toUpperCase()}`;
            } else {
                displayName = user.name + " " + user.surname;
            }

            await userRef.set({
                displayName: displayName
            }, {
                merge: true
            });

            console.log(`User ${userRef.id}'s display name has been changed to "${displayName}".`);
        })());

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