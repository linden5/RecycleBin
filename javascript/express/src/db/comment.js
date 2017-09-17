function commentSetup(db) {
    return db.define('t_blog', {
        id: {type: 'number', key: true},
        content: {type: 'text'},
        create_at: {type: 'date'},
        commenter: {type: 'text'}
    });
}

module.exports = commentSetup;