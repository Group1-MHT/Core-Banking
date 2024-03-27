export const environment = {
    production: false,
    authorize_uri: 'http://localhost:9000/oauth2/authorize?',
    client_id : 'client',
    redirect_uri: 'http://localhost:9000/login',
    scope: 'openid profile',
    response_type: 'code',
    response_mode: 'form_post',
    code_challenge_method: 'S256',
    code_challenge: 'meWMSrkRBnMbd5RRseIVwuFGFOU6xwHi0_LgQyCOd1A',
    code_verifier: 'vaJQvOcb8RfWoZWFvpSLAQjUywZx2VUiG6cAZpr7MV7'
  };