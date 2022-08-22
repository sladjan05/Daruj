import * as functions from "firebase-functions";

import { configureUser } from "./configureUser";

export const configureUserCreation = functions.firestore
    .document("users/{userId}")
    .onCreate(async (snapshot, context) => {
        const user = snapshot.data();
        await configureUser(user, snapshot.ref);
    });