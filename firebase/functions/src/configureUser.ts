import * as admin from "firebase-admin";

export const configureUser = async (
    user: admin.firestore.DocumentData, 
    userRef: admin.firestore.DocumentReference<admin.firestore.DocumentData>
) => {
    let displayName: string;

    // Display Name
    if (user.private) {
        // TODO: Better ID generation
        displayName = "Korisnik " + userRef.id.substring(0, 5).toUpperCase();
    } else {
        displayName = user.name + " " + user.surname;
    }

    try {
        await userRef.set({
            displayName: displayName
        }, {
            merge: true
        });

        console.log(`User ${userRef.id}'s display name has been changed to "${displayName}".`);
    } catch(e) {
        console.log(`An error occured: ${e}`);
    }
}