package com.blessing333.springbasic.console.voucher.ui;

import com.blessing333.springbasic.console.ui.ApacheCommandLine;
import com.blessing333.springbasic.console.ui.CommandOptionConfigurer;
import com.blessing333.springbasic.console.ui.CommandOptions;
import com.blessing333.springbasic.domain.voucher.dto.VoucherCreateFormPayload;
import com.blessing333.springbasic.domain.voucher.model.Voucher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@Profile("console")
@RequiredArgsConstructor
public class ApacheCliVoucherManagerUserInterface extends ApacheCommandLine implements VoucherManagerUserInterface {
    private static final String AVAILABLE_VOUCHER_TYPE_GUIDE = initAvailableVoucherTypeGuideText();
    private final Scanner scanner = new Scanner(System.in);

    private static String initAvailableVoucherTypeGuideText() {
        StringBuilder sb = new StringBuilder();
        Voucher.VoucherType.getValidVoucherTypes().forEach((optionNumber, voucherType) ->
                sb.append(optionNumber).append(". ").append(voucherType.getDescription()).append("\n")
        );
        return sb.toString();
    }

    @Override
    public void printVoucherInformation(Voucher voucher) {
        printDivider();
        printMessage(voucher.toString());
        printDivider();
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public String requestMessage() {
        return scanner.nextLine();
    }

    @Override
    public void printGuide() {
        printMessage("=== Voucher Program ===");
        printMessage("Type exit to exit the program.");
        printMessage("Type create to create a new voucher.");
        printMessage("Type list to list all vouchers.");
        printMessage("=======================");
        printMessage("명령을 입력해주세요");
    }

    @Override
    public void printVoucherTypeGuide() {
        printMessage("생성할 바우쳐의 타입을 입력하세요.");
        printMessage(AVAILABLE_VOUCHER_TYPE_GUIDE);
    }

    @Override
    public VoucherCreateFormPayload requestVoucherInformation() {
        printVoucherTypeGuide();
        String voucherType = requestMessage();
        printMessage("할인 금액 혹은 비율을 입력하세요");
        String discountAmount = requestMessage();
        return new VoucherCreateFormPayload(voucherType, discountAmount);
    }

    @Override
    public void printRegisterComplete(Voucher voucher) {
        printMessage("바우쳐 생성이 완료되었습니다.");
        printVoucherInformation(voucher);
    }

    @Override
    protected CommandOptions initSupportedCommandOption() {
        return CommandOptionConfigurer.configSupportedCommandOptions(VoucherCommandOptionType.getAvailableCommandOptionType());
    }

    @Override
    public void printHelp() {
        printHelpText("바우처 관리");
    }
}
