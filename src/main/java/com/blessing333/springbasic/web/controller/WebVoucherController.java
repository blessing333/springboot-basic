package com.blessing333.springbasic.web.controller;

import com.blessing333.springbasic.domain.voucher.converter.ConvertFailException;
import com.blessing333.springbasic.domain.voucher.converter.VoucherPayloadConverter;
import com.blessing333.springbasic.domain.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.domain.voucher.dto.VoucherCreateFormPayload;
import com.blessing333.springbasic.domain.voucher.dto.VoucherUpdateForm;
import com.blessing333.springbasic.domain.voucher.dto.VoucherUpdateFormPayload;
import com.blessing333.springbasic.domain.voucher.model.Voucher;
import com.blessing333.springbasic.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("vouchers")
@Slf4j
public class WebVoucherController {
    public static final String VOUCHER_REGISTRY_VIEW = "voucher/registry-view";
    public static final String VOUCHER_VOUCHER_VIEW = "voucher/voucher-view";
    public static final String VOUCHER_VOUCHERS_VIEW = "voucher/vouchers-view";
    public static final String VOUCHER_UPDATE_VIEW = "voucher/update-view";
    private static final String PAYLOAD_MODEL_ATTRIBUTE = "payload";
    private static final String MESSAGE_MODEL_ATTRIBUTE = "message";
    private static final String ERROR_MODEL_ATTRIBUTE = "message";

    private final VoucherService service;
    private final VoucherPayloadConverter converter;

    @GetMapping()
    public String getVoucherListView(Model model) {
        List<Voucher> vouchers = service.loadAllVoucher();
        model.addAttribute("vouchers", vouchers);
        return VOUCHER_VOUCHERS_VIEW;
    }

    @PostMapping()
    public String createNewVoucher(@Valid @ModelAttribute VoucherCreateFormPayload payload,
                                   Errors errors, Model model, RedirectAttributes attributes
    ) {
        if (errors.hasErrors()) {
            log.error(errors.getAllErrors().toString());
            model.addAttribute(PAYLOAD_MODEL_ATTRIBUTE, payload);
            return VOUCHER_REGISTRY_VIEW;
        }
        try{
            VoucherCreateForm form = converter.toCreateForm(payload);
            Voucher voucher = service.registerVoucher(form);
            attributes.addFlashAttribute(MESSAGE_MODEL_ATTRIBUTE, "바우처 생성 완료.");
            return "redirect:/vouchers/" + voucher.getVoucherId();
        } catch (ConvertFailException e){
            log.error(e.getMessage(),e);
            model.addAttribute(ERROR_MODEL_ATTRIBUTE,"잘못된 값이 있습니다.");
            model.addAttribute(PAYLOAD_MODEL_ATTRIBUTE,payload);
            return VOUCHER_REGISTRY_VIEW;
        }
    }

    @GetMapping("/{voucherId}")
    public String getVoucherView(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = service.loadVoucherById(voucherId);
        model.addAttribute("voucher", voucher);
        return VOUCHER_VOUCHER_VIEW;
    }

    @PatchMapping("/{voucherId}")
    public String update(@ModelAttribute @Valid VoucherUpdateFormPayload payload,
                         @PathVariable UUID voucherId,
                         Model model, Errors errors, RedirectAttributes attributes
    ) {
        if (errors.hasErrors()) {
            log.error(errors.getAllErrors().toString());
            model.addAttribute(PAYLOAD_MODEL_ATTRIBUTE,payload);
            return VOUCHER_UPDATE_VIEW;
        }
        try{
            VoucherUpdateForm form = converter.toUpdateForm(payload);
            service.updateVoucher(form);
            attributes.addFlashAttribute(MESSAGE_MODEL_ATTRIBUTE, "바우처 수정 완료.");
            return "redirect:/vouchers/" + voucherId;
        }catch (ConvertFailException e){
            log.error(e.getMessage(),e);
            model.addAttribute(ERROR_MODEL_ATTRIBUTE,"잘못된 값이 있습니다.");
            model.addAttribute(PAYLOAD_MODEL_ATTRIBUTE,payload);
            return VOUCHER_UPDATE_VIEW;
        }

    }

    @DeleteMapping("/{voucherId}")
    public String removeVoucher(@PathVariable UUID voucherId, RedirectAttributes attributes) {
        service.deleteVoucher(voucherId);
        attributes.addFlashAttribute(MESSAGE_MODEL_ATTRIBUTE, "바우처 삭제 완료");
        return "redirect:/vouchers";
    }

    @GetMapping("/registry-form")
    public String getVoucherCreateView(Model model) {
        model.addAttribute(PAYLOAD_MODEL_ATTRIBUTE, new VoucherCreateFormPayload());
        return VOUCHER_REGISTRY_VIEW;
    }

    @GetMapping("/update-form/{voucherId}")
    public String getVoucherUpdateView(@PathVariable UUID voucherId, Model model) {
        Voucher voucher = service.loadVoucherById(voucherId);
        var payload = VoucherUpdateFormPayload.fromEntity(voucher);
        model.addAttribute(PAYLOAD_MODEL_ATTRIBUTE, payload);
        return VOUCHER_UPDATE_VIEW;
    }
}
