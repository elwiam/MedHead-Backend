-- Supprimer les tables existantes s'il y en a
DROP TABLE IF EXISTS Hospital_Speciality;
DROP TABLE IF EXISTS Speciality;
DROP TABLE IF EXISTS Hospital;
DROP TABLE IF EXISTS Reservation;
DROP TABLE IF EXISTS Role;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS User;

-- Créer la table Hospital
CREATE TABLE Hospital (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  latitude DECIMAL(10, 8),
  longitude DECIMAL(11, 8),
  availableBeds INT,
  address VARCHAR(255),
  postalCode VARCHAR(50) -- Ajout de la colonne postalCode
);

-- Créer la table Speciality
CREATE TABLE Speciality (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255)
);

-- Créer la table Hospital_Speciality
CREATE TABLE Hospital_Speciality (
  hospital_id BIGINT,
  speciality_id BIGINT,
  FOREIGN KEY (hospital_id) REFERENCES Hospital(id),
  FOREIGN KEY (speciality_id) REFERENCES Speciality(id)
);

-- Créer la table Role
CREATE TABLE Role (
  role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  password VARCHAR(100) -- Ajout de la colonne password
);

-- Créer la table User
CREATE TABLE User (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  email VARCHAR(100) NOT NULL,
  enabled BOOLEAN NOT NULL,
  first_name VARCHAR(75),
  last_name VARCHAR(80)
);

-- Créer la table user_roles
CREATE TABLE user_roles (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL DEFAULT 0,
  PRIMARY KEY (user_id, role_id),
  FOREIGN KEY (user_id) REFERENCES User(id),
  FOREIGN KEY (role_id) REFERENCES Role(role_id)
);

-- Insérer les rôles
INSERT INTO Role (name, password)
VALUES ('ROLE_ADMIN', ''),
       ('ROLE_USER', '');

-- Vérifier si le rôle 'ROLE_USER' existe déjà
INSERT INTO Role (name, password)
SELECT 'ROLE_USER', ''
WHERE NOT EXISTS (SELECT 1 FROM Role WHERE name = 'ROLE_USER');

-- Insérer les utilisateurs
INSERT INTO User (username, password, email, enabled, first_name, last_name)
VALUES ('admin', '$2a$10$/FmjWVrPGCrkc0Fud.kmnOOTvXjGRPkcl4vm7dm5IQ/DuuNLH0e9y', 'admin@gmail.com', true, 'Admin', 'User'),
       ('user', '$2a$10$xO7Y4h45FqxLxqTOlZErneFafZBrX9KUxdSYXphBS0TycSwGNnmwS', 'user@gmail.com', true, 'Regular', 'User');

-- Créer la table Reservation
CREATE TABLE Reservation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    hospital_id BIGINT NOT NULL,
    speciality_id BIGINT NOT NULL,
    num_patients INT NOT NULL,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hospital_id) REFERENCES Hospital(id),
    FOREIGN KEY (speciality_id) REFERENCES Speciality(id)
);



-- Insérer des données dans la table Hospital en Île-de-France
INSERT INTO Hospital (name, latitude, longitude, availableBeds, address, postalCode) VALUES
  ('Hôpital Bichat-Claude Bernard', 48.8964, 2.3318, 100, '46 Rue Henri Huchard', '75018'),
  ('Hôpital Pitié-Salpêtrière', 48.8375, 2.3624, 50, '47-83 Boulevard de l''Hôpital', '75013'),
  ('Hôpital Necker-Enfants Malades', 48.8472, 2.3122, 200, '149 Rue de Sèvres', '75015'),
  ('Hôpital Cochin', 48.8386, 2.3365, 150, '27 Rue du Faubourg Saint-Jacques', '75014'),
  ('Hôpital Saint-Louis', 48.8739, 2.3631, 80, '1 Avenue Claude Vellefaux', '75010'),
  ('Hôpital Henri Mondor', 48.8057, 2.4471, 120, '51 Avenue du Maréchal de Lattre de Tassigny', '94010');

-- Insérer des données dans la table Speciality
INSERT INTO Speciality (name) VALUES
  ('Cardiology'),
  ('Neurology'),
  ('Orthopedics'),
  ('Dermatology'),
  ('Gastroenterology');


-- Insérer des données dans la table Hospital_Speciality
INSERT INTO Hospital_Speciality (hospital_id, speciality_id) VALUES
  (1, 1),
  (1, 2),
  (2, 2),
  (2, 3),
  (3, 3);

-- Insérer des données dans la table Reservation
INSERT INTO Reservation (patient_name, phone_number, hospital_id, speciality_id, num_patients)
VALUES ('John Lia', '1234567890', (SELECT id FROM Hospital WHERE name = 'Hôpital Bichat-Claude Bernard'), (SELECT id FROM Speciality WHERE name = 'Cardiology'), 2),
       ('Jane Smith', '9876543210', (SELECT id FROM Hospital WHERE name = 'Hôpital Pitié-Salpêtrière'), (SELECT id FROM Speciality WHERE name = 'Neurology'), 1),
       ('Michael Johnson', '5555555555', (SELECT id FROM Hospital WHERE name = 'Hôpital Necker-Enfants Malades'), (SELECT id FROM Speciality WHERE name = 'Orthopedics'), 3);


ALTER TABLE reservation ADD COLUMN num_beds INT;

