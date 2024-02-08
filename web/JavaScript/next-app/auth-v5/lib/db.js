import {PrismaClient} from "@prisma/client";

const PrismaContext = {
    db: new PrismaClient(),
}

const db = PrismaContext.db || new PrismaClient();
if (process.env.NODE_ENV !== "production") {
    console.log("reconnect to the db");
    PrismaContext.db = db;
}

export default PrismaContext