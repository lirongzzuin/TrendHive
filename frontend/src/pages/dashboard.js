import { useEffect, useState } from "react";
import { useRouter } from "next/router";

export default function Dashboard() {
  const [trends, setTrends] = useState([]);
  const router = useRouter();
  const handleCreateTrend = () => {
    router.push("/create-trend");
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      router.push("/login"); // 로그인되지 않은 사용자는 로그인 페이지로 리디렉트
      return;
    }

    console.log("JWT 토큰:", token);

    fetch("http://localhost:8080/api/trends", {
      headers: { Authorization: `Bearer ${token}` },
    })
      .then((res) => {
        if (!res.ok) {
          throw new Error("서버 응답 실패");
        }
        return res.json();
      })
      .then((data) => setTrends(data))
      .catch((err) => {
        console.error("트렌드 목록 불러오기 실패:", err);
        alert("트렌드 목록을 불러오는 데 실패했습니다.");
      });
  }, []);

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold">🔥 TrendHive 트렌드 목록</h1>
      <button
        onClick={handleCreateTrend}
        className="bg-blue-600 text-white px-4 py-2 rounded mb-4 hover:bg-blue-700"
      >
        + 트렌드 등록하기
      </button>
      <ul>
        {/* 트렌드 리스트를 렌더링하고, 각 항목 클릭 시 상세 페이지로 이동 */}
        {/* 트렌드 제목을 클릭하면 상세 페이지로 이동 */}
        {trends.map((trend) => (
          <li
            key={trend.id}
            className="p-4 border rounded-lg shadow-sm mb-4 hover:bg-gray-50 cursor-pointer"
            onClick={() => router.push(`/trend/${trend.id}`)}
          >
            <h2 className="text-lg font-semibold">{trend.title}</h2>
            <p className="text-sm text-gray-600">{trend.description}</p>
            <div className="text-xs text-gray-400 mt-1">
              작성자: {trend.createdBy?.username || "알 수 없음"} · 댓글 {trend.commentCount}개 · 좋아요 {trend.likeCount}개
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}
