import * as functions from "firebase-functions";
import * as admin from "firebase-admin";

export const configureStorage = functions.storage
    .object()
    .onFinalize(async (object, context) => {
        const name = object.name!
        const fileDirs = name.split("/");

        // Configure profile pictures
        if(fileDirs[fileDirs.length - 2] === "users") {
            const fileName = fileDirs[fileDirs.length - 1].split(".")[0]

            try {
                const userDocument = await admin
                    .firestore()
                    .collection("users")
                    .doc(fileName)
                    .get()

                const user = userDocument.data();

                await admin
                    .storage()
                    .bucket()
                    .file(name)
                    .setMetadata({
                        metadata: {
                            private: user!.private
                        }
                    });

                console.log(`Profile picture metadata of user ${userDocument.id} has been changed.`);
            } catch(e) {
                console.log(`An error occured: ${e}`);
            }
        }
    });