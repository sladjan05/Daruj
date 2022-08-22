import * as functions from "firebase-functions";

export const configureUserCreation = functions.firestore
    .document("posts/{postId}")
    .onCreate(async (snapshot, context) => {
        let timestamp = snapshot.createTime.toDate()
        
        try {
            await snapshot.ref
                .set({
                    timestamp: timestamp,
                    commentCount: 0,
                    shareCount: 0,
                    donorCount: 0
                }, {
                    merge: true
                });
        } catch(e) {
            console.log(`An error occured: ${e}`);
        }
    });