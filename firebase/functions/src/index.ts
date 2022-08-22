import * as admin from "firebase-admin";

const serviceAccountKey = require("../serviceAccountKey.json")
admin.initializeApp({
    credential: admin.credential.cert(serviceAccountKey),
    storageBucket: "gs://daruj-ba.appspot.com"
});

import * as srcConfigureUserCreation from "./configureUserCreation";
import * as srcConfigureUserUpdate from "./configureUserUpdate";
import * as srcConfigurePostCreation from "./configurePostCreation";
import * as srcConfigureStorage from "./configureStorage";
import * as srcGetUser from "./getUser";

export const configureUserCreation = srcConfigureUserCreation.configureUserCreation;
export const configureUserUpdate = srcConfigureUserUpdate.configureUserUpdate;
export const configurePostCreation = srcConfigurePostCreation.configureUserCreation;
export const configureStorage = srcConfigureStorage.configureStorage;
export const getUser = srcGetUser.getUser;