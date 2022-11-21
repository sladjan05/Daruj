import * as admin from "firebase-admin";
import * as functions from "firebase-functions";

export const configureUserUpdate = functions.firestore
    .document("users/{userId}")
    .onUpdate(async (change, context) => {
        if (change.after.isEqual(change.before)) return;

        const userId = context.params.userId;
        const user = change.after.data();
        const prevUser = change.before.data();

        const isPrivateChanged = user.private !== prevUser.private;

        if (!isPrivateChanged && user.private) return;

        console.log(`Detected changes on user ${userId}.`);

        // Change profile picture metadata property `private` to match `user.private`
        if (isPrivateChanged) {
            try {
                await admin.storage()
                           .bucket()
                           .file(`users/${userId}.png`)
                           .setMetadata({
                               metadata: {
                                   private: user.private.toString()
                               }
                           });
            } catch (e: any) {
                if (e.code !== 404) console.log(`An error occured: ${e}`);
            }
        }
    });