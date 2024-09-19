db = db.getSiblingDB("querylicesys_dev");

db.createUser({
    user: "umongo",
    pwd: "pmongo",
    roles:[
        {
            role: 'readWrite',
            db: 'querylicesys_dev'
        }
    ]

});

db.createCollection("licenses");