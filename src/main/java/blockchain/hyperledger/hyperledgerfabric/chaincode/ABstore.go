package main

import (
	"encoding/json"
	"fmt"
	"log"
	"strconv"

	"github.com/hyperledger/fabric-contract-api-go/contractapi"
)

const (
	errFailedToGetState   = "failed to get state"
	errNilAmountForEntity = "nil amount for %s"
)

type ABstore struct {
	contractapi.Contract
}

type Token struct {
	ID          int           `json:"id"`
	Owner       string        `json:"owner"`
	TransferLog []TransferLog `json:"transferLog"`
	TokenURI    string        `json:"tokenURI"`
	TicketCount int           `json:"ticketCount"`
}

type TransferLog struct {
	From string `json:"from"`
	To   string `json:"to"`
	Time string `json:"time"`
}

func newError(format string, args ...interface{}) error {
	return fmt.Errorf("error: "+format, args...)
}

func logError(err error) {
	log.Printf("error: %v", err)
}

func (t *ABstore) Init(ctx contractapi.TransactionContextInterface, A string, Aval int, B string, Bval int) error {
	fmt.Println("ABstore Init")
	fmt.Printf("Aval = %d, Bval = %d\n", Aval, Bval)

	if err := ctx.GetStub().PutState(A, []byte(strconv.Itoa(Aval))); err != nil {
		return newError("failed to put state for A: %v", err)
	}

	if err := ctx.GetStub().PutState(B, []byte(strconv.Itoa(Bval))); err != nil {
		return newError("failed to put state for B: %v", err)
	}
	return nil
}

func (t *ABstore) Invoke(ctx contractapi.TransactionContextInterface, A, B string, X int, toAddress string, tokenId int, tokenURI string) error {
	Aval, err := t.getStateValue(ctx, A)
	if err != nil {
		return newError(errFailedToGetState)
	}

	Bval, err := t.getStateValue(ctx, B)
	if err != nil {
		return newError(errFailedToGetState)
	}

	Aval -= X
	Bval += X
	fmt.Printf("Aval = %d, Bval = %d\n", Aval, Bval)

	if err := ctx.GetStub().PutState(A, []byte(strconv.Itoa(Aval))); err != nil {
		return newError("failed to put state for A: %v", err)
	}

	if err := ctx.GetStub().PutState(B, []byte(strconv.Itoa(Bval))); err != nil {
		return newError("failed to put state for B: %v", err)
	}

	if err := t.MintERC721Token(ctx, toAddress, tokenId, tokenURI); err != nil {
		return err
	}
	return nil
}

func (t *ABstore) getStateValue(ctx contractapi.TransactionContextInterface, key string) (int, error) {
	valueBytes, err := ctx.GetStub().GetState(key)
	if err != nil {
		return 0, fmt.Errorf("failed to get state value for %s: %v", key, err)
	}

	if valueBytes == nil {
		return 0, newError(errNilAmountForEntity, key)
	}

	value, _ := strconv.Atoi(string(valueBytes))
	return value, nil
}

func (t *ABstore) MintERC721Token(ctx contractapi.TransactionContextInterface, toAddress string, tokenId int, tokenURI string) error {
	if err := t.checkTokenExistence(ctx, tokenId); err != nil {
		return err
	}

	token := &Token{
		ID:       tokenId,
		Owner:    toAddress,
		TokenURI: tokenURI,
	}

	tokenJSON, err := json.Marshal(token)
	if err != nil {
		return newError("failed to marshal token: %v", err)
	}

	if err := ctx.GetStub().PutState(strconv.Itoa(tokenId), tokenJSON); err != nil {
		return newError("failed to mint token: %v", err)
	}
	return nil
}

func (t *ABstore) checkTokenExistence(ctx contractapi.TransactionContextInterface, tokenId int) error {
	tokenJSON, err := ctx.GetStub().GetState(strconv.Itoa(tokenId))
	if err != nil {
		return newError("failed to check existing token: %v", err)
	}

	if tokenJSON != nil {
		return newError("token with ID %d already exists", tokenId)
	}
	return nil
}

func (t *ABstore) TransferERC721Token(ctx contractapi.TransactionContextInterface, fromAddress, toAddress string, tokenId int) error {
	token, err := t.checkTokenValidity(ctx, tokenId)
	if err != nil {
		return err
	}

	if token.Owner != fromAddress {
		return newError("token with ID %d is not owned by %s", tokenId, fromAddress)
	}

	token.Owner = toAddress
	if err := t.updateTokenState(ctx, tokenId, token); err != nil {
		return err
	}

	return nil
}

func (t *ABstore) checkTokenValidity(ctx contractapi.TransactionContextInterface, tokenId int) (*Token, error) {
	tokenJSON, err := ctx.GetStub().GetState(strconv.Itoa(tokenId))
	if err != nil {
		return nil, newError("failed to get token: %v", err)
	}

	if tokenJSON == nil {
		return nil, newError("token with ID %d does not exist", tokenId)
	}

	token := new(Token)
	if err := json.Unmarshal(tokenJSON, &token); err != nil {
		return nil, newError("failed to unmarshal token: %v", err)
	}

	return token, nil
}

func (t *ABstore) updateTokenState(ctx contractapi.TransactionContextInterface, tokenId int, token *Token) error {
	tokenJSON, err := json.Marshal(token)
	if err != nil {
		return newError("failed to marshal token: %v", err)
	}

	if err := ctx.GetStub().PutState(strconv.Itoa(tokenId), tokenJSON); err != nil {
		return newError("failed to update token state: %v", err)
	}

	return nil
}

func (t *ABstore) QueryERC721Token(ctx contractapi.TransactionContextInterface, tokenId int) (*Token, error) {
	tokenJSON, err := ctx.GetStub().GetState(strconv.Itoa(tokenId))
	if err != nil {
		return nil, newError("failed to get token: %v", err)
	}

	if tokenJSON == nil {
		return nil, newError("token with ID %d does not exist", tokenId)
	}

	token := new(Token)
	if err := json.Unmarshal(tokenJSON, &token); err != nil {
		return nil, newError("failed to unmarshal token: %v", err)
	}

	return token, nil
}

func (t *ABstore) Delete(ctx contractapi.TransactionContextInterface, A string) error {
	err := ctx.GetStub().DelState(A)
	if err != nil {
		return newError("failed to delete state: %v", err)
	}
	return nil
}

func (t *ABstore) Query(ctx contractapi.TransactionContextInterface, A string) (string, error) {
	Avalbytes, err := ctx.GetStub().GetState(A)
	if err != nil {
		return "", newError("failed to get state: %v", err)
	}

	if Avalbytes == nil {
		return "", newError("nil amount for %s", A)
	}
	return string(Avalbytes), nil
}

func main() {
	cc, err := contractapi.NewChaincode(new(ABstore))
	if err != nil {
		panic(err.Error())
	}
	if err := cc.Start(); err != nil {
		fmt.Printf("error starting ABstore chaincode: %s", err)
	}
}
