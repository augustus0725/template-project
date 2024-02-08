import PrismaContext from "@/lib/db";

import {NextResponse} from "next/server";
import {console} from "next/dist/compiled/@edge-runtime/primitives";

export async function POST(req) {
    const body = await req.json();
    const name = body.name || "anonymous";
    console.log(body);
    await PrismaContext.db.user.create({data: {...body, name: name}})
    // const users = await PrismaContext.db.user.findMany();
    // console.log("user is :", users);
    return NextResponse.json({status: "success"});
}