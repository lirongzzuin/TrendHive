

import { useRouter } from 'next/router'
import { useEffect, useState } from 'react'

export default function TrendDetailPage() {
  const router = useRouter()
  const { id } = router.query
  const [trend, setTrend] = useState(null)

  useEffect(() => {
    if (id) {
      fetch(`/api/trends/${id}`)
        .then(res => res.json())
        .then(data => setTrend(data))
    }
  }, [id])

  if (!trend) return <p>로딩 중...</p>

  return (
    <div style={{ padding: '2rem' }}>
      <h1>{trend.title}</h1>
      <p>{trend.description}</p>
      <p>카테고리: {trend.category}</p>
      <p>작성자: {trend.username}</p>
      <p>좋아요 수: {trend.likeCount}</p>
      <p>댓글 수: {trend.commentCount}</p>
      <a href={trend.sourceUrl} target="_blank" rel="noopener noreferrer">출처 보기</a>
    </div>
  )
}