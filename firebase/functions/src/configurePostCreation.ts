import * as functions from "firebase-functions";

export const configurePostCreation = functions.firestore
    .document("posts/{postId}")
    .onCreate(async (snapshot, context) => {
        const postId = context.params.postId;
        const timestamp = snapshot.createTime.toDate();
        
        const data = snapshot.data();

        try {
            await snapshot.ref
                          .set({
                              searchCriteria: `${data.name.toLowerCase()} ${data.surname.toLowerCase()}`,
                              timestamp: timestamp,
                              shareCount: 0,
                              donorCount: 0,
                              comments: []
                          }, {
                              merge: true
                          });
            
            console.log(`Post ${postId} has been configured.`);
        } catch (e) {
            console.log(`An error occured: ${e}`);
        }
    });