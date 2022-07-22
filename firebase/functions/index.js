const functions = require("firebase-functions");

const admin = require("firebase-admin");
admin.initializeApp()

const firestore = admin.firestore();

exports.configureUserCreation = functions.firestore
    .document("users/{userId}")
    .onCreate((snapshot, context) => {
        const user = snapshot.data();
        return configureUser(user, snapshot.ref);
    });

exports.configureUserUpdate = functions.firestore
    .document("users/{userId}")
    .onUpdate((change, context) => {
        const user = change.after.data();
        const prevUser = change.before.data();

        let isPrimaryDataChanged = user.name !== prevUser.name || user.surname !== prevUser.surname;
        let isPrivateChanged = user.isPrivate !== prevUser.isPrivate;

        if (!isPrivateChanged && user.isPrivate) return false;
        if (!isPrimaryDataChanged && !isPrivateChanged) return false;

        return configureUser(user, change.after.ref);
    });

function configureUser(user, userRef) {
    let displayName;

    // Display Name
    if (user.isPrivate) {
        displayName = "Korisnik " + Math.floor(Math.random() * 10000).toString();
    } else {
        displayName = user.name + " " + user.surname;
    }

    return userRef.set(
        {
            displayName: displayName
        },
        {
            merge: true
        }
    );
}