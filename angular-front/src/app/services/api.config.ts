// Every request goes through the gateway (port 9999), which finds the services in Eureka.
export const GATEWAY_URL = 'http://localhost:9999';
export const EBANK_SERVICE_URL = `${GATEWAY_URL}/EBANK-SERVICE`;
export const CUSTOMER_SERVICE_URL = `${GATEWAY_URL}/CUSTOMER-SERVICE`;
export const EBANK_BOT_URL = `${GATEWAY_URL}/EBANK-BOT`;
