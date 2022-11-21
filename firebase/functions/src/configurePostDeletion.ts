import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

import { FieldValue } from "firebase-admin/firestore";

const firestore = admin.firestore();

export const configurePostDeletion = functions.firestore
    .document("posts/{postId}")
    .onDelete(async (snapshot, context) => {
        const postId = context.params.postId;
        
        try {
            const userSnapshots = await firestore.collection("users")
                                                 .where("savedPosts", "array-contains", postId)
                                                 .get();

            const userSavesPromises: Promise<any>[] = [];
            const arrayRemove = FieldValue.arrayRemove(postId);

            userSnapshots.forEach(userSnapshot => {
                const promise = userSnapshot.ref.update({savedPosts: arrayRemove});
                userSavesPromises.push(promise);
            });

            const commentPromises: Promise<any>[] = [];
            const commentSnapshots = await firestore.collection("comments")
                                                    .where("postId", "==", postId)
                                                    .get();
            commentSnapshots.forEach(snapshot => {
                const promise = snapshot.ref.delete();
                commentPromises.push(promise);
            });

            await Promise.all(userSavesPromises);
            console.log(`Post ${postId} has been removed from saves for all users.`);

            await Promise.all(commentPromises);
            console.log(`All comments for post ${postId} have been deleted.`);
        } catch (e) {
            console.log(`An error occured: ${e}`);
        }
    });