// src/pages/index.js
import { useRouter } from "next/router";
import { useEffect } from "react";
import Head from "next/head";

export default function Home() {
    const router = useRouter();

    useEffect(() => {
        router.replace("/dashboard");
    }, [router]);

    return (
        <>
            <Head>
                <title>TrendHive</title>
                <meta name="description" content="TrendHive Dashboard Redirect" />
            </Head>
        </>
    );
}