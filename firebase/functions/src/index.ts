import * as admin from "firebase-admin";

const serviceAccountKey = require("../serviceAccountKey.json");
admin.initializeApp({
    credential: admin.credential.cert(serviceAccountKey),
    storageBucket: "gs://daruj-ba.appspot.com"
});

export * from "./configureUserCreation";
export * from "./configureUserUpdate";
export * from "./configurePostCreation";
export * from "./configureStorage";
export * from "./getUser";