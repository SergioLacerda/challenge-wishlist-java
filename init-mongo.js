// init-mongo.js
db = db.getSiblingDB('admin'); // Connect to the admin database

db.createUser({
  user: 'root',
  pwd: 'secret',
  roles: [{ role: 'root', db: 'admin' }]
});

db = db.getSiblingDB('wishlist-db'); // Connect to the wishlist-db database

// Optional: Create initial data or collections