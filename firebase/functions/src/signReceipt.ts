import * as admin from "firebase-admin";
import * as functions from "firebase-functions";

import { Transaction, FieldValue } from "firebase-admin/firestore";

import { notifyUser } from "./notifyUser";

export const signReceipt = functions.https
    .onCall(async (data, context) => {
        const postId = data.postId;
        const userId = data.userId;
        const isApproved = data.isApproved;
        
        const storagePromise = admin.storage()
                                    .bucket()
                                    .file(`receipts/${postId}/${userId}.png`)
                                    .delete();

        const userDataSnapshotPromise = admin.firestore()
                                             .collection("userData")
                                             .doc(userId)
                                             .get();
  
        const postRef = admin.firestore()
                             .collection("posts")
                             .doc(postId);

        if (isApproved) {            
            const userDonationPromise = admin.firestore()
                                             .runTransaction(async (transaction: Transaction) => {
                                                try {
                                                    const arrayUnion = FieldValue.arrayUnion({
                                                        postId: postId,
                                                        timestamp: new Date()
                                                    });

                                                    const userRef = admin.firestore()
                                                                         .collection("users")
                                                                         .doc(userId);

                                                    const userSnapshot = await userRef.get();
                                                    const points = userSnapshot.data()!.points;

                                                    return transaction.update(
                                                        userRef, 
                                                        {
                                                            donations: arrayUnion,
                                                            points: points + 100
                                                        }
                                                    );
                                                } catch (e) {
                                                    console.log(`An error occured: ${e}`);
                                                    return null;
                                                }
                                             });
            
            const postPromise = admin.firestore()
                                     .runTransaction(async (transaction: Transaction) => {
                                         try {
                                             const postSnapshot = await transaction.get(postRef);
                                             const donorCount = postSnapshot.data()!.donorCount;
            
                                             return transaction.update(postRef, {donorCount: donorCount + 1});
                                         } catch(e) {
                                             console.log(`An error occured: ${e}`);
                                             return null;
                                         }
                                     });

            try {
                await userDonationPromise;
                console.log(`Donation for post ${postId} has been written to user ${userId}.`);

                await postPromise;
                console.log(`Count of donors has been increased for post ${postId}.`);
            } catch (e) {
                console.log(`An error occured: ${e}`);
            }
        }
        
        try {
            await storagePromise;
            console.log(`Receipt ${userId} for post ${postId} has been deleted.`);

            await notifyUser(
                await userDataSnapshotPromise,
                {
                    type: "RECEIPT_APPROVAL",
                    postId: postId,
                    isApproved: isApproved.toString()
                }
            );
        } catch (e) {
            console.log(`An error occured: ${e}`);
        }
    });