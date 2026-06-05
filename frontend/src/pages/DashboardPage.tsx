import { useEffect, useState } from 'react';
import { api } from '../api/client';
import type { Dashboard, NotificationItem, Enrollment } from '../types';

export default function DashboardPage() {
  const [dashboard, setDashboard] = useState<Dashboard | null>(null);
  const [notifications, setNotifications] = useState<NotificationItem[]>([]);

  // States for progression editing
  const [editingId, setEditingId] = useState<number | null>(null);
  const [tempProgress, setTempProgress] = useState<number>(0);

  // States for course rating
  const [ratingId, setRatingId] = useState<number | null>(null);
  const [tempNote, setTempNote] = useState<number>(5);
  const [tempCommentaire, setTempCommentaire] = useState<string>('');

  const loadData = () => {
    api.get('/users/me/dashboard').then((res) => setDashboard(res.data)).catch(() => undefined);
    api.get('/notifications').then((res) => setNotifications(res.data)).catch(() => undefined);
  };

  useEffect(() => {
    loadData();
  }, []);

  const startEdit = (enrollment: Enrollment) => {
    setEditingId(enrollment.id);
    setTempProgress(enrollment.progression);
    setRatingId(null);
  };

  const startRating = (enrollmentId: number) => {
    setRatingId(enrollmentId);
    setTempNote(5);
    setTempCommentaire('');
    setEditingId(null);
  };

  const saveProgress = async (id: number) => {
    try {
      await api.put(`/enrollments/${id}`, { progression: tempProgress });
      setEditingId(null);
      loadData();
    } catch (err) {
      console.error(err);
    }
  };

  const saveRating = async (courseId: number) => {
    try {
      await api.post('/ratings', { courseId, note: tempNote, commentaire: tempCommentaire });
      setRatingId(null);
      loadData();
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div className="page-stack">
      <section className="page-heading">
        <div>
          <span className="badge">Suivi de progression</span>
          <h1>Tableau de bord</h1>
          <p>Suivez vos cours, votre progression et vos notifications.</p>
        </div>
      </section>

      <section className="stats-grid">
        <Stat label="Cours suivis" value={dashboard?.totalCours ?? 0} />
        <Stat label="Cours complétés" value={dashboard?.coursCompletes ?? 0} />
        <Stat label="Progression moyenne" value={`${dashboard?.progressionMoyenne ?? 0}%`} />
        <Stat label="Note moyenne donnée" value={`${dashboard?.noteMoyenneDonnee ?? 0}/5`} />
      </section>

      <section className="grid-2 align-start">
        <div className="glass-card padded">
          <h2>Mes cours</h2>
          <div className="list-cards">
            {dashboard?.enrollments?.map((enrollment) => (
              <article key={enrollment.id} className="mini-card" style={{ display: 'grid', gap: '10px' }}>
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                  <div>
                    <strong>{enrollment.course.titre}</strong>
                    <p style={{ margin: '4px 0 0', fontSize: '0.85rem' }}>{enrollment.course.categorie} · {enrollment.course.niveau}</p>
                  </div>
                  <div>
                    <button className="btn btn-soft" style={{ padding: '6px 10px', fontSize: '0.78rem' }} onClick={() => startRating(enrollment.id)}>
                      ⭐ Noter
                    </button>
                  </div>
                </div>

                <div className="mini-progress">
                  <span style={{ width: `${enrollment.progression}%` }} />
                </div>

                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                  <small>{enrollment.progression}% terminé</small>
                  <button className="btn btn-soft" style={{ padding: '6px 10px', fontSize: '0.78rem' }} onClick={() => startEdit(enrollment)}>
                    ✏️ Progression
                  </button>
                </div>

                {editingId === enrollment.id && (
                  <div className="glass-card" style={{ padding: '12px', marginTop: '8px', display: 'grid', gap: '8px', border: '1px solid rgba(255,255,255,0.1)' }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                      <span style={{ fontSize: '0.9rem', fontWeight: 600 }}>Modifier la progression</span>
                      <strong style={{ fontSize: '0.9rem' }}>{tempProgress}%</strong>
                    </div>
                    <input
                      type="range"
                      min="0"
                      max="100"
                      value={tempProgress}
                      onChange={(e) => setTempProgress(parseInt(e.target.value))}
                      style={{ cursor: 'pointer' }}
                    />
                    <div style={{ display: 'flex', gap: '8px' }}>
                      <button className="btn btn-primary" style={{ padding: '6px 12px', fontSize: '0.8rem', flex: 1 }} onClick={() => saveProgress(enrollment.id)}>
                        Enregistrer
                      </button>
                      <button className="btn btn-soft" style={{ padding: '6px 12px', fontSize: '0.8rem' }} onClick={() => setEditingId(null)}>
                        Annuler
                      </button>
                    </div>
                  </div>
                )}

                {ratingId === enrollment.id && (
                  <div className="glass-card" style={{ padding: '12px', marginTop: '8px', display: 'grid', gap: '8px', border: '1px solid rgba(255,255,255,0.1)' }}>
                    <span style={{ fontSize: '0.9rem', fontWeight: 600 }}>Évaluer le cours</span>
                    <div style={{ display: 'flex', alignItems: 'center', gap: '8px' }}>
                      <label style={{ fontSize: '0.8rem', fontWeight: 500, display: 'inline' }}>Note :</label>
                      <select
                        value={tempNote}
                        onChange={(e) => setTempNote(parseInt(e.target.value))}
                        style={{ padding: '6px', fontSize: '0.85rem' }}
                      >
                        <option value="5">⭐⭐⭐⭐⭐ (5/5)</option>
                        <option value="4">⭐⭐⭐⭐ (4/5)</option>
                        <option value="3">⭐⭐⭐ (3/5)</option>
                        <option value="2">⭐⭐ (2/5)</option>
                        <option value="1">⭐ (1/5)</option>
                      </select>
                    </div>
                    <div>
                      <textarea
                        placeholder="Votre commentaire (optionnel)..."
                        value={tempCommentaire}
                        onChange={(e) => setTempCommentaire(e.target.value)}
                        rows={2}
                        style={{ fontSize: '0.85rem', padding: '8px' }}
                      />
                    </div>
                    <div style={{ display: 'flex', gap: '8px' }}>
                      <button className="btn btn-primary" style={{ padding: '6px 12px', fontSize: '0.8rem', flex: 1 }} onClick={() => saveRating(enrollment.course.id)}>
                        Valider
                      </button>
                      <button className="btn btn-soft" style={{ padding: '6px 12px', fontSize: '0.8rem' }} onClick={() => setRatingId(null)}>
                        Annuler
                      </button>
                    </div>
                  </div>
                )}
              </article>
            ))}
          </div>
        </div>
        <div className="glass-card padded">
          <h2>Notifications</h2>
          <div className="notification-list">
            {notifications.map((n) => <div key={n.id} className={n.lu ? 'notification read' : 'notification'}>{n.message}</div>)}
          </div>
        </div>
      </section>
    </div>
  );
}

function Stat({ label, value }: { label: string; value: string | number }) {
  return (
    <div className="stat-card">
      <span>{label}</span>
      <strong>{value}</strong>
    </div>
  );
}

