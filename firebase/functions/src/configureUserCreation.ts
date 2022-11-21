import * as functions from "firebase-functions";

export const configureUserCreation = functions.firestore
    .document("users/{userId}")
    .onCreate(async (snapshot, context) => {
        const userRef = snapshot.ref;

        try {
            await userRef.set({
                savedPosts: [],
                donations: [],
                points: 0
            }, {
                merge: true
            });
        } catch (e) {
            console.log(`An error occured: ${e}`);
        }
    });