import { useRouter } from 'next/router'
import { useEffect, useState } from 'react'

export default function TrendDetailPage() {
  const router = useRouter()
  const { id } = router.query
  const [trend, setTrend] = useState(null)
  const [comments, setComments] = useState([])
  const [newComment, setNewComment] = useState('')
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (id) {
      setLoading(true);
      Promise.all([
        fetch(`/api/trends/${id}`).then(res => {
          if (!res.ok) throw new Error('Trend fetch failed');
          return res.json();
        }),
        fetch(`/api/trends/${id}/comments`).then(res => {
          if (!res.ok) throw new Error('Comments fetch failed');
          return res.json();
        }),
      ])
        .then(([trendData, commentData]) => {
          setTrend(trendData);
          setComments(commentData);
          setError(null);
        })
        .catch((err) => {
          console.error(err);
          setError('데이터를 불러오는 중 문제가 발생했습니다.');
        })
        .finally(() => setLoading(false));
    }
  }, [id])

  const handleAddComment = async () => {
    if (!newComment.trim()) return
    const res = await fetch(`/api/comments`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
      body: JSON.stringify({ trendId: id, content: newComment }),
    })
    if (res.ok) {
      setNewComment('');
      fetch(`/api/trends/${id}/comments`)
        .then(res => res.json())
        .then(data => setComments(data));
    }
  }

  if (loading) return <p>로딩 중...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div style={{ padding: '2rem' }}>
      <h1>{trend.title}</h1>
      <p>{trend.description}</p>
      <p>카테고리: {trend.category}</p>
      <p>작성자: {trend.username}</p>
      <p>좋아요 수: {trend.likeCount}</p>
      <p>댓글 수: {trend.commentCount}</p>
      <a href={trend.sourceUrl} target="_blank" rel="noopener noreferrer">출처 보기</a>

      <hr style={{ margin: '2rem 0' }} />
      <h2>댓글</h2>
      <div>
        {comments.map(comment => (
          <div key={comment.id} style={{ marginBottom: '1rem' }}>
            <p><strong>{comment.username}</strong> | {new Date(comment.createdAt).toLocaleString()}</p>
            <p>{comment.content}</p>
          </div>
        ))}
      </div>
      <div style={{ marginTop: '1rem' }}>
        <textarea
          value={newComment}
          onChange={(e) => setNewComment(e.target.value)}
          rows={3}
          style={{ width: '100%', padding: '0.5rem' }}
          placeholder="댓글을 입력하세요"
        />
        <button onClick={handleAddComment} style={{ marginTop: '0.5rem' }}>댓글 등록</button>
      </div>
    </div>
  )
}