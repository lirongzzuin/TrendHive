import { useEffect, useState } from "react";
import { useRouter } from "next/router";

export default function Dashboard() {
  const [trends, setTrends] = useState([]);
  const router = useRouter();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      router.push("/login"); // 로그인되지 않은 사용자는 로그인 페이지로 리디렉트
      return;
    }

    // 클래스가 최종 내어나는 모든 트렌드 데이터를 얻기 위해
    // backend 에서 객체의 경로가 /api/trends/all로 정의되어 있으며
    // 방해되는 오픈 정확할 수 있도록 수정
    fetch("http://localhost:8080/api/trends/all", {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then((res) => res.json())
      .then((data) => setTrends(data));
  }, []);

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold">🔥 TrendHive 트렌드 목록</h1>
      <ul>
        {trends.map((trend) => (
          <li key={trend.id} className="p-2 border-b">{trend.title}</li>
        ))}
      </ul>
    </div>
  );
}
