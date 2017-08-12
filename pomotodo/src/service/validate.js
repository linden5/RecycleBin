export default {
    validateEmail: function(email) {
        return /[\d\w\.]+@[\d\w]+\.\w+(\.\w+)*/.test(email);
    }
}