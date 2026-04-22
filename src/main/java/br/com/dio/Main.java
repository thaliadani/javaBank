package br.com.dio;

import br.com.dio.exception.*;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.MoneyAudit;
import br.com.dio.repository.AccountRepository;
import br.com.dio.repository.InvestmentRepository;
import br.com.dio.repository.CommonsRepository;

import java.util.InputMismatchException;
import java.util.Arrays;
import java.util.Scanner;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class Main {

    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestmentRepository investmentRepository = new InvestmentRepository();

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Ola seja bem vindo ao DIO Bank");
        while (true){
            System.out.println("Selecione a operação desejada");
            System.out.println("1 - Criar uma conta");
            System.out.println("2 - Criar um investimento");
            System.out.println("3 - Criar uma carteira de investimento");
            System.out.println("4 - Depositar na conta");
            System.out.println("5 - Sacar da conta");
            System.out.println("6 - Transferencia entre contas");
            System.out.println("7 - Investir");
            System.out.println("8 - Sacar investimento");
            System.out.println("9 - Listar contas");
            System.out.println("10 - Listar Investimentos");
            System.out.println("11 - Listar carteiras de investimento");
            System.out.println("12 - Atualizar investimentos");
            System.out.println("13 - Historico de conta");
            System.out.println("14 - Sair");
            try {
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        createAccount();
                        break;
                    case 2:
                        createInvestment();
                        break;
                    case 3:
                        createWalletInvestment();
                        break;
                    case 4:
                        deposit();
                        break;
                    case 5:
                        withdraw();
                        break;
                    case 6:
                        transferToAccount();
                        break;
                    case 7:
                        incInvestment();
                        break;
                    case 8:
                        rescueInvestment();
                        break;
                    case 9:
                        accountRepository.list().forEach(System.out::println);
                        break;
                    case 10:
                        investmentRepository.list().forEach(System.out::println);
                        break;
                    case 11:
                        investmentRepository.listWallets().forEach(System.out::println);
                        break;
                    case 12:
                        investmentRepository.updateAmount();
                        System.out.println("Investimentos reajustados");
                        break;
                    case 13:
                        checkHistory();
                        break;
                    case 14:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número.");
                scanner.nextLine(); // Limpa o buffer
            } catch (Exception ex) {
                System.out.println("Erro inesperado: " + ex.getMessage());
            }
        }
    }

    private static void createAccount(){
        System.out.println("Informe as chaves pix (separadas por ';'");
        java.util.List<String> pix = Arrays.stream(scanner.next().split(";")).toList();
        System.out.println("Informe o valor inicial de deposito");
        long amount = scanner.nextLong();
        CommonsRepository.checkPositiveAmount(amount);
        try {
            AccountWallet wallet = accountRepository.create(pix, amount);
            System.out.println("Conta criada: " + wallet);
        } catch (PixInUseException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void createInvestment(){
        System.out.println("Informe a taxa do investimento");
        int tax = scanner.nextInt();
        System.out.println("Informe o valor inicial de deposito");
        long initialFunds = scanner.nextLong();
        CommonsRepository.checkPositiveAmount(initialFunds);
        br.com.dio.model.Investment investment = investmentRepository.create(tax, initialFunds);
        System.out.println("investimento criado: " + investment);
    }

    private static void withdraw(){
        System.out.println("Informe a chave pix da conta para saque:");
        String pix = scanner.next();
        System.out.println("Informe o valor que será sacado: ");
        long amount = scanner.nextLong();
        CommonsRepository.checkPositiveAmount(amount);
        try {
            accountRepository.withdraw(pix, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void deposit(){
        System.out.println("Informe a chave pix da conta para deposito:");
        String pix = scanner.next();
        System.out.println("Informe o valor que será depositado: ");
        long amount = scanner.nextLong();
        CommonsRepository.checkPositiveAmount(amount);
        try{
            accountRepository.deposit(pix, amount);
        } catch (AccountNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void transferToAccount(){
        System.out.println("Informe a chave pix da conta de origgem:");
        String source = scanner.next();
        System.out.println("Informe a chave pix da conta de destino:");
        String target = scanner.next();
        System.out.println("Informe o valor que será depositado: ");
        long amount = scanner.nextLong();
        CommonsRepository.checkPositiveAmount(amount);
        try{
            accountRepository.transferMoney(source, target, amount);
        } catch (AccountNotFoundException | NoFundsEnoughException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void createWalletInvestment(){
        System.out.println("Informe a chave pix da conta:");
        String pix = scanner.next();
        try {
            AccountWallet account = accountRepository.findByPix(pix);
            System.out.println("Informe o identificador do investimento");
            long investmentId = scanner.nextLong();
            br.com.dio.model.InvestmentWallet investmentWallet = investmentRepository.initInvestment(account, investmentId);
            System.out.println("Conta de investimento criada: " + investmentWallet);
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void incInvestment(){
        System.out.println("Informe a chave pix da conta para investimento:");
        String pix = scanner.next();
        System.out.println("Informe o valor que será investido: ");
        long amount = scanner.nextLong();
        CommonsRepository.checkPositiveAmount(amount);
        try{
            investmentRepository.deposit(pix, amount);
        } catch (WalletNotFoundException | AccountNotFoundException | NoFundsEnoughException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static void rescueInvestment(){
        System.out.println("Informe a chave pix da conta para resgate do investimento:");
        String pix = scanner.next();
        System.out.println("Informe o valor que será sacado: ");
        long amount = scanner.nextLong();
        CommonsRepository.checkPositiveAmount(amount);
        try {
            investmentRepository.withdraw(pix, amount);
        } catch (NoFundsEnoughException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void checkHistory(){
        System.out.println("Informe a chave pix da conta para verificar extrato:");
        String pix = scanner.next();
        try {
            java.util.Map<java.time.OffsetDateTime, java.util.List<MoneyAudit>> sortedHistory = accountRepository.getHistory(pix);
            sortedHistory.forEach((k, v) -> {
                System.out.println("--- " + k.format(ISO_DATE_TIME) + " ---");
                v.forEach(audit -> {
                    System.out.println("  ID: " + audit.transactionId());
                    System.out.println("  [" + audit.targetService() + "] " + audit.description());
                    System.out.println("  Valor: R$" + String.format("%d,%02d", audit.amount() / 100, audit.amount() % 100));
                });
            });
        } catch (AccountNotFoundException ex){
            System.out.println(ex.getMessage());
        }
    }

}