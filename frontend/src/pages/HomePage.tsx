import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { api } from '../api/client';
import { useAuth } from '../state/AuthContext';
import type { Book, Course, NotificationItem } from '../types';

const domains = [
  { name: 'Data & IA', icon: '🤖', text: 'Machine learning, data science, Python' },
  { name: 'Développement Web', icon: '💻', text: 'React, Spring Boot, APIs, UI moderne' },
  { name: 'IoT', icon: '📡', text: 'Capteurs, systèmes embarqués, cloud' },
  { name: 'Cybersecurity', icon: '🔐', text: 'Sécurité, JWT, protection des données' },
  { name: 'Business', icon: '📈', text: 'Gestion, marketing, productivité' },
  { name: 'Design', icon: '🎨', text: 'UX/UI, identité visuelle, prototypage' },
];

export default function HomePage() {
  const { user } = useAuth();
  const [recommendations, setRecommendations] = useState<Course[]>([]);
  const [books, setBooks] = useState<Book[]>([]);
  const [notifications, setNotifications] = useState<NotificationItem[]>([]);

  useEffect(() => {
    api.get('/recommendations').then((res) => setRecommendations(res.data)).catch(() => undefined);
    api.get('/books/free').then((res) => setBooks(res.data.slice(0, 4))).catch(() => undefined);
    api.get('/notifications').then((res) => setNotifications(res.data.slice(0, 3))).catch(() => undefined);
  }, []);

  return (
    <div className="page-stack">
      <section className="hero-card">
        <div>
          <span className="badge">Bonjour {user?.name}</span>
          <h1>Votre bibliothèque e-learning selon votre domaine.</h1>
          <p>Découvrez des cours, des livres gratuits et des recommandations personnalisées pour avancer étape par étape.</p>
          <div className="hero-actions">
            <Link className="btn btn-primary" to="/catalogue">Explorer les cours</Link>
            <Link className="btn btn-soft" to="/livres">Voir les livres gratuits</Link>
          </div>
        </div>
        <div className="hero-panel glass-card">
          <strong>Domaine actuel</strong>
          <h2>{user?.domaineInteret}</h2>
          <p>Niveau : {user?.niveau}</p>
          <div className="mini-progress"><span style={{ width: '72%' }} /></div>
        </div>
      </section>

      <section>
        <div className="section-title"><h2>Bibliothèques par domaine</h2><p>Choisissez un domaine pour filtrer les ressources.</p></div>
        <div className="domain-grid">
          {domains.map((d) => (
            <Link key={d.name} className="domain-card" to={`/livres?domaine=${encodeURIComponent(d.name)}`}>
              <span>{d.icon}</span>
              <strong>{d.name}</strong>
              <p>{d.text}</p>
            </Link>
          ))}
        </div>
      </section>

      <section className="grid-2">
        <div className="glass-card padded">
          <div className="section-title compact"><h2>Top recommandations</h2><Link to="/catalogue">Tout voir</Link></div>
          <div className="list-cards">
            {recommendations.map((course) => (
              <article key={course.id} className="mini-card">
                <span className="chip">{course.categorie}</span>
                <strong>{course.titre}</strong>
                <p>{course.niveau} · ⭐ {course.noteMoyenne.toFixed(1)}</p>
              </article>
            ))}
          </div>
        </div>
        <div className="glass-card padded">
          <div className="section-title compact"><h2>Livres gratuits</h2><Link to="/livres">Gestion</Link></div>
          <div className="list-cards">
            {books.map((book) => (
              <article key={book.id} className="mini-card book-row">
                <span className="book-cover">📘</span>
                <div><strong>{book.titre}</strong><p>{book.domaine} · {book.auteur}</p></div>
              </article>
            ))}
          </div>
        </div>
      </section>

      <section className="glass-card padded">
        <div className="section-title compact"><h2>Notifications</h2><span>{notifications.length} nouvelles</span></div>
        <div className="notification-list">
          {notifications.map((n) => <div key={n.id} className={n.lu ? 'notification read' : 'notification'}>{n.message}</div>)}
        </div>
      </section>
    </div>
  );
}
