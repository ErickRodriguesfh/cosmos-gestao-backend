INSERT INTO users (name, email, sector, username, password) VALUES
('John Doe', 'john.doe@example.com', 'IT', 'johndoe', 'securepassword1'),
('Jane Doe', 'jane.doe@example.com', 'HR', 'janedoe', 'securepassword2'),
('Alice Smith', 'alice.smith@example.com', 'Finance', 'alicesmith', 'securepassword3')
ON CONFLICT (username) DO NOTHING;

INSERT INTO roles (id, name) VALUES (1, 'admin')
ON CONFLICT (id) DO NOTHING;

INSERT INTO roles (id, name) VALUES (2, 'basic')
ON CONFLICT (id) DO NOTHING;
