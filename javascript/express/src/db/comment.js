const getTime = require('../util/dateUtil');

function commentSetup(db) {
    return db.define('t_blog', {
        id: {type: 'number', key: true},
        content: {type: 'text'},
        create_at: {type: 'date'},
        commenter: {type: 'text'}
    }, {
        methods: {
            getCreateTime() {
                return getTime(this.create_at);
            }
        }
    });
}

module.exports = commentSetup;