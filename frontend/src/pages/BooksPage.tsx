import { FormEvent, useEffect, useMemo, useState } from 'react';
import { useSearchParams } from 'react-router-dom';
import { api } from '../api/client';
import type { Book } from '../types';

const emptyBook = {
  titre: '',
  auteur: '',
  domaine: 'Data & IA',
  niveau: 'Débutant',
  description: '',
  lien: '',
  couverture: '',
  gratuit: true,
};

export default function BooksPage() {
  const [params] = useSearchParams();
  const initialDomain = params.get('domaine') || '';
  const [books, setBooks] = useState<Book[]>([]);
  const [domaine, setDomaine] = useState(initialDomain);
  const [keyword, setKeyword] = useState('');
  const [form, setForm] = useState(emptyBook);
  const [editId, setEditId] = useState<number | null>(null);

  const loadBooks = () => {
    api.get('/books', { params: { domaine, keyword } })
      .then((res) => setBooks(res.data))
      .catch(() => setBooks([]));
  };

  useEffect(() => { loadBooks(); }, []);

  const domains = useMemo(() => Array.from(new Set(books.map((b) => b.domaine))).sort(), [books]);

  const submit = async (e: FormEvent) => {
    e.preventDefault();
    if (editId) {
      await api.put(`/books/${editId}`, form);
    } else {
      await api.post('/books', form);
    }
    setForm(emptyBook);
    setEditId(null);
    loadBooks();
  };

  const startEdit = (book: Book) => {
    setEditId(book.id);
    setForm({
      titre: book.titre,
      auteur: book.auteur,
      domaine: book.domaine,
      niveau: book.niveau,
      description: book.description,
      lien: book.lien,
      couverture: book.couverture || '',
      gratuit: book.gratuit,
    });
  };

  const remove = async (id: number) => {
    if (!confirm('Supprimer ce livre ?')) return;
    await api.delete(`/books/${id}`);
    loadBooks();
  };

  return (
    <div className="page-stack">
      <section className="page-heading">
        <div>
          <span className="badge">Bibliothèque digitale</span>
          <h1>Livres gratuits et gestion des livres</h1>
          <p>Ajoutez des ressources PDF, guides et livres ouverts selon le domaine.</p>
        </div>
        <div className="stats-pill">{books.length} livres</div>
      </section>

      <section className="grid-2 align-start">
        <div>
          <div className="filters glass-card small">
            <input value={keyword} onChange={(e) => setKeyword(e.target.value)} placeholder="Chercher un livre..." />
            <input value={domaine} onChange={(e) => setDomaine(e.target.value)} placeholder="Domaine" list="domains" />
            <datalist id="domains">{domains.map((d) => <option key={d} value={d} />)}</datalist>
            <button className="btn btn-primary" onClick={loadBooks}>Filtrer</button>
          </div>
          <div className="book-grid">
            {books.map((book) => (
              <article key={book.id} className="book-card">
                <div className="book-icon">📘</div>
                <div>
                  <span className="chip">{book.domaine}</span>
                  <h3>{book.titre}</h3>
                  <p>{book.description}</p>
                  <small>{book.auteur} · {book.niveau} · {book.gratuit ? 'Gratuit' : 'Payant'}</small>
                  <div className="book-actions">
                    <a className="btn btn-soft" href={book.lien || '#'} target="_blank">Ouvrir</a>
                    <button className="btn btn-soft" onClick={() => startEdit(book)}>Modifier</button>
                    <button className="btn btn-danger" onClick={() => remove(book.id)}>Supprimer</button>
                  </div>
                </div>
              </article>
            ))}
          </div>
        </div>

        <aside className="glass-card padded sticky-card">
          <h2>{editId ? 'Modifier le livre' : 'Ajouter un livre'}</h2>
          <form onSubmit={submit} className="form-grid">
            <label>Titre<input value={form.titre} onChange={(e) => setForm({ ...form, titre: e.target.value })} required /></label>
            <label>Auteur<input value={form.auteur} onChange={(e) => setForm({ ...form, auteur: e.target.value })} required /></label>
            <label>Domaine<input value={form.domaine} onChange={(e) => setForm({ ...form, domaine: e.target.value })} required /></label>
            <label>Niveau<select value={form.niveau} onChange={(e) => setForm({ ...form, niveau: e.target.value })}><option>Débutant</option><option>Intermédiaire</option><option>Avancé</option></select></label>
            <label>Description<textarea value={form.description} onChange={(e) => setForm({ ...form, description: e.target.value })} rows={4} required /></label>
            <label>Lien gratuit<input value={form.lien} onChange={(e) => setForm({ ...form, lien: e.target.value })} placeholder="https://..." /></label>
            <label className="checkbox-row"><input type="checkbox" checked={form.gratuit} onChange={(e) => setForm({ ...form, gratuit: e.target.checked })} /> Livre gratuit</label>
            <button className="btn btn-primary full">{editId ? 'Enregistrer' : 'Ajouter'}</button>
            {editId && <button type="button" className="btn btn-soft full" onClick={() => { setEditId(null); setForm(emptyBook); }}>Annuler</button>}
          </form>
        </aside>
      </section>
    </div>
  );
}
