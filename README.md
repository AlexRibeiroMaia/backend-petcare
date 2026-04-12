# backend-petcare

## Executando localmente

1. Copie o arquivo de exemplo e ajuste as variaveis:
   - `cp .env.example .env`
2. Suba o backend:
   - `./mvnw spring-boot:run`

## Testar com Expo no celular fisico

1. Deixe computador e celular na mesma rede Wi-Fi.
2. Descubra o IP local do computador:
   - macOS: `ipconfig getifaddr en0`
3. No app Expo (projeto mobile), configure a API com o IP da maquina:
   - `EXPO_PUBLIC_API_URL=http://SEU_IP_LOCAL:8080`
4. Inicie o Expo:
   - `npx expo start --lan`
5. Escaneie o QR Code no Expo Go.

Observacoes:
- No celular, nao use `localhost` para chamar a API; use o IP da sua maquina.
- CORS ja esta preparado para origens locais e LAN via `APP_CORS_ALLOWED_ORIGIN_PATTERNS`.
- Em app nativo (Expo Go), CORS normalmente nao bloqueia; ele e mais relevante para `expo web`.
