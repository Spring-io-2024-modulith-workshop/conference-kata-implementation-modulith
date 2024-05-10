# PROPOSAL API CURL Commands for Manual Testing or Reference

## Submit a proposal

```bash
curl -X POST "http://localhost:8080/proposals" -H "Content-Type: application/json" -d '{
    "title": "Test Proposal",
    "description": "This is a test proposal.",
    "speakerId": "a82028f8-dea5-4bec-b436-407dfb87b211"
}' | jq
```

## Get all proposals

```bash
curl -X GET "http://localhost:8080/proposals" | jq
```

## Get a specific proposal by ID

```bash
curl -X GET "http://localhost:8080/proposals/a2d7610a-f1fc-4f3b-80d5-15b242a62b47" | jq
```

## Approve a proposal

```bash
curl -X PATCH "http://localhost:8080/proposals/a2d7610a-f1fc-4f3b-80d5-15b242a62b47/approve"
```

## Reject a proposal

```bash
curl -X PATCH "http://localhost:8080/proposals/a2d7610a-f1fc-4f3b-80d5-15b242a62b47/reject"
```
