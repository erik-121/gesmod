module.exports = application => {

    application.get('/', (req, res) => 
        application.app.controllers.index.index(req, res, application));

};