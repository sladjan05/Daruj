import * as admin from "firebase-admin";
import * as functions from "firebase-functions";

export const getUser = functions.https
    .onCall(async (data, context) => {
        const userDocument = await admin
            .firestore()
            .doc(`/users/${data}`)
            .get()

        const user = userDocument.data();
        
        return JSON.stringify({
            id: data,
            displayName: user!.displayName,
            sex: user!.sex,
            blood: user!.blood
        });
    });