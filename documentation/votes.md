# Votes API CURL Commands for Manual Testing or Reference

## Voting for a proposal

```bash
curl -X POST "http://localhost:8080/voting/proposal" \
    -H "Content-Type: application/json" \
    -d '{
          "userId": "51d205e8-d274-4923-9623-c4f23aea0ce5",
          "proposalId": "43736d30-28c0-4e2c-9c1d-f8e37c929f0e",
          "rating": 5
        }'
```

### Voting for a talk

```shell
curl -X POST "http://localhost:8080/voting/talk" \
    -H "Content-Type: application/json" \
    -d '{
          "userId": "51d205e8-d274-4923-9623-c4f23aea0ce5",
          "proposalId": "43736d30-28c0-4e2c-9c1d-f8e37c929f0e",
          "rating": 5
        }'
```
