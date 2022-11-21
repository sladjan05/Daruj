import * as admin from "firebase-admin";
import * as functions from "firebase-functions";

import { getDisplayName } from "./getDisplayName";

export const getUser = functions.https
    .onCall(async (userId, context) => {
        const userSnapshot = await admin.firestore()
                                        .collection("users")
                                        .doc(userId.toString())
                                        .get();

        const userData = userSnapshot.data()!;
        
        return JSON.stringify({
            id: userId,
            displayName: getDisplayName(userSnapshot),
            sex: userData.sex,
            blood: userData.blood,
            points: userData.points
        });
    });