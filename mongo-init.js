db.createUser(
        {
            user: "mongodb-admin",
            pwd: "mongodb-password",
            roles: [
                {
                    role: "readWrite",
                    db: "work-item-service"
                }
            ]
        }
);