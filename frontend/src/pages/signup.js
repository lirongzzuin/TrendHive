import { useState } from "react";
import { useRouter } from "next/router";

export default function Signup() {
    const [form, setForm] = useState({ username: "", email: "", password: "" });
    const [error, setError] = useState("");
    const router = useRouter();

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");

        try {
            const res = await fetch("http://localhost:8080/api/users/register", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(form),
            });

            if (res.ok) {
                router.push("/login");
            } else {
                const data = await res.json();
                setError(data.message || "회원가입 실패");
            }
        } catch (err) {
            setError("서버 오류가 발생했습니다.");
        }
    };

    return (
        <div className="p-4 max-w-md mx-auto">
            <h1 className="text-2xl font-bold mb-4">회원가입</h1>
            <form onSubmit={handleSubmit} className="space-y-4">
                <input name="username" onChange={handleChange} placeholder="닉네임" className="border p-2 w-full" required />
                <input name="email" type="email" onChange={handleChange} placeholder="이메일" className="border p-2 w-full" required />
                <input name="password" type="password" onChange={handleChange} placeholder="비밀번호" className="border p-2 w-full" required />
                {error && <p className="text-red-500">{error}</p>}
                <button type="submit" className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700">
                    회원가입
                </button>
            </form>
        </div>
    );
}