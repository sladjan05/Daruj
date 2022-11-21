import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

import { notifyUser } from "./notifyUser";

export const configureStorage = functions.storage
    .object()
    .onFinalize(async (object, context) => {
        const name = object.name!
        const fileDirs = name.split("/");

        if (fileDirs[fileDirs.length - 2] === "users") {
            const userId = fileDirs[fileDirs.length - 1].split(".")[0]

            try {
                const userSnapshot = await admin.firestore()
                                                .collection("users")
                                                .doc(userId)
                                                .get();

                const userData = userSnapshot.data();

                await admin.storage()
                           .bucket()
                           .file(name)
                           .setMetadata({
                               metadata: {
                                   private: userData!.private
                               }
                           });

                console.log(`Profile picture metadata of user ${userSnapshot.id} has been changed.`);
            } catch (e) {
                console.log(`An error occured: ${e}`);
            }
        } else if (fileDirs[fileDirs.length - 3] === "receipts") {
            const userId = fileDirs[fileDirs.length - 1].split(".")[0];
            const postId = fileDirs[fileDirs.length - 2];

            const postSnapshotPromise = admin.firestore()
                                             .collection("posts")
                                             .doc(postId)
                                             .get();

            try {
                const postSnapshot = await postSnapshotPromise;
                const postData = postSnapshot.data()!;

                const postUserDataSnapshotPromise = admin.firestore()
                                                         .collection("userData")
                                                         .doc(postData.userId)
                                                         .get();

                await notifyUser(
                    await postUserDataSnapshotPromise,
                    {
                        type: "PENDING_RECEIPT",
                        postId: postId,
                        userId: userId
                    }
                );
            } catch (e) {
                console.log(`An error occured: ${e}`);
            }
        }
    });