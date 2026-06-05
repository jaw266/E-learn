import { useEffect, useMemo, useState } from 'react';
import { api } from '../api/client';
import type { Course } from '../types';

const niveaux = ['', 'Débutant', 'Intermédiaire', 'Avancé'];
const categories = ['', 'Data & IA', 'Développement Web', 'IoT', 'Cybersecurity', 'Business', 'Design'];

export default function CatalogPage() {
  const [courses, setCourses] = useState<Course[]>([]);
  const [keyword, setKeyword] = useState('');
  const [categorie, setCategorie] = useState('');
  const [niveau, setNiveau] = useState('');

  const loadCourses = () => {
    api.get('/courses', { params: { keyword, categorie, niveau } })
      .then((res) => setCourses(res.data))
      .catch(() => setCourses([]));
  };

  useEffect(() => { loadCourses(); }, []);

  const totalHours = useMemo(() => courses.reduce((acc, c) => acc + c.dureeHeures, 0), [courses]);

  return (
    <div className="page-stack">
      <section className="page-heading">
        <div>
          <span className="badge">Catalogue intelligent</span>
          <h1>Cours par domaine et niveau</h1>
          <p>Recherche, filtres et inscription rapide pour démarrer votre apprentissage.</p>
        </div>
        <div className="stats-pill">{courses.length} cours · {totalHours}h</div>
      </section>

      <section className="filters glass-card">
        <input value={keyword} onChange={(e) => setKeyword(e.target.value)} placeholder="Chercher un cours..." />
        <select value={categorie} onChange={(e) => setCategorie(e.target.value)}>{categories.map((c) => <option key={c} value={c}>{c || 'Tous les domaines'}</option>)}</select>
        <select value={niveau} onChange={(e) => setNiveau(e.target.value)}>{niveaux.map((n) => <option key={n} value={n}>{n || 'Tous les niveaux'}</option>)}</select>
        <button className="btn btn-primary" onClick={loadCourses}>Filtrer</button>
      </section>

      <section className="course-grid">
        {courses.map((course) => <CourseCard key={course.id} course={course} />)}
      </section>
    </div>
  );
}

function CourseCard({ course }: { course: Course }) {
  const [done, setDone] = useState(false);
  const enroll = async () => {
    await api.post('/enrollments', { courseId: course.id });
    setDone(true);
  };
  return (
    <article className="course-card">
      <div className="course-top">
        <span className="chip">{course.categorie}</span>
        <span>⭐ {course.noteMoyenne.toFixed(1)}</span>
      </div>
      <h3>{course.titre}</h3>
      <p>{course.description}</p>
      <div className="course-meta">
        <span>{course.niveau}</span>
        <span>{course.dureeHeures}h</span>
        {course.gratuit && <span>Gratuit</span>}
      </div>
      <button className="btn btn-primary full" onClick={enroll} disabled={done}>{done ? 'Inscrit' : 'Commencer'}</button>
    </article>
  );
}
