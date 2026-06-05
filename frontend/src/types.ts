export type User = {
  name: string;
  email: string;
  niveau: string;
  domaineInteret: string;
  role: string;
};

export type Course = {
  id: number;
  titre: string;
  description: string;
  categorie: string;
  niveau: string;
  noteMoyenne: number;
  dureeHeures: number;
  gratuit: boolean;
  imageUrl?: string;
};

export type Book = {
  id: number;
  titre: string;
  auteur: string;
  domaine: string;
  niveau: string;
  description: string;
  lien: string;
  couverture?: string;
  gratuit: boolean;
};

export type Enrollment = {
  id: number;
  progression: number;
  completed: boolean;
  course: Course;
};

export type Dashboard = {
  totalCours: number;
  coursCompletes: number;
  progressionMoyenne: number;
  noteMoyenneDonnee: number;
  enrollments: Enrollment[];
};

export type NotificationItem = {
  id: number;
  message: string;
  lu: boolean;
  dateCreation: string;
};
