import * as admin from "firebase-admin";
import * as functions from "firebase-functions";
import { notifyUser } from "./notifyUser";

export const configureCommentCreation = functions.firestore
    .document("comments/{commentId}")
    .onCreate(async (snapshot, context) => {
        const commentId = context.params.commentId;
        const commentData = snapshot.data()!;

        const postId = commentData.postId;

        const timestamp = snapshot.createTime.toDate();
        
        try {
            const commentConfigurationPromise =  snapshot.ref
                                                         .set({
                                                             timestamp: timestamp
                                                         }, {
                                                             merge: true
                                                         });

            const postSnapshot = await admin.firestore()
                                            .collection("posts")
                                            .doc(snapshot.data().postId)
                                            .get();

            const userId = postSnapshot.data()!.userId;
            const userDataSnapshotPromise = admin.firestore()
                                                 .collection("userData")
                                                 .doc(userId)
                                                 .get();

            const notifyUserPromise = notifyUser(
                await userDataSnapshotPromise,
                {
                    type: "NEW_COMMENT",
                    commentId: commentId,
                    postId: postId
                }
            );
            
            await commentConfigurationPromise;
            console.log(`Comment ${commentId} has been configured.`);

            await notifyUserPromise;
            console.log(`User ${userId} has been notified about new comment.`);
        } catch (e) {
            console.log(`An error occured: ${e}`);
        }
    });