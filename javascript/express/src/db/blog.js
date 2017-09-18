const getTime = require('../util/dateUtil');

function blogSetup(db) {
    return db.define('t_blog', {
        id: {type: 'number', key: true},
        title: {type: 'text'},
        content: {type: 'text'},
        create_at: {type: 'date'},
        modified_at: {type: 'date'}
    }, {
        methods: {
            getCreateTime: function() {
                return getTime(this.create_at);
            },
            getLastModified: function() {
                return getTime(this.modified_at);
            }
        }
    });
}

module.exports = blogSetup;