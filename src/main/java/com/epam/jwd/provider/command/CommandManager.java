package com.epam.jwd.provider.command;

import com.epam.jwd.provider.command.admin.ChangeSubscriptionStatusCommand;
import com.epam.jwd.provider.command.admin.ChangeUserStatusCommand;
import com.epam.jwd.provider.command.admin.DeleteAccountCommand;
import com.epam.jwd.provider.command.admin.DeleteSubscriptionCommand;
import com.epam.jwd.provider.command.admin.DeleteTariffCommand;
import com.epam.jwd.provider.command.admin.FindSubscriptionCommand;
import com.epam.jwd.provider.command.admin.TariffServiceCommand;
import com.epam.jwd.provider.command.admin.FindUserCommand;
import com.epam.jwd.provider.command.admin.ViewDebtorsCommand;
import com.epam.jwd.provider.command.admin.ViewSubscriptionRequestsCommand;
import com.epam.jwd.provider.command.page.ShowAdminPage;
import com.epam.jwd.provider.command.page.ShowMainPage;
import com.epam.jwd.provider.command.page.ShowSettingsPage;
import com.epam.jwd.provider.command.page.ShowSubscriptionPage;
import com.epam.jwd.provider.command.page.ShowSubscriptionSettingsPage;
import com.epam.jwd.provider.command.page.ShowTariffSettingsPage;
import com.epam.jwd.provider.command.page.ShowTariffsPage;
import com.epam.jwd.provider.command.page.ShowUserLoginPage;
import com.epam.jwd.provider.command.page.ShowUserProfilePage;
import com.epam.jwd.provider.command.page.ShowUsersForAdminPage;
import com.epam.jwd.provider.command.page.ShowUserSignUpPage;
import com.epam.jwd.provider.command.user.CancelSubscriptionCommand;
import com.epam.jwd.provider.command.user.ChangePasswordCommand;
import com.epam.jwd.provider.command.user.ContactSupportCommand;
import com.epam.jwd.provider.command.user.LoginCommand;
import com.epam.jwd.provider.command.user.LogoutCommand;
import com.epam.jwd.provider.command.user.SetLocalizationCommand;
import com.epam.jwd.provider.command.user.SignUpCommand;
import com.epam.jwd.provider.command.user.SubscribeCommand;
import com.epam.jwd.provider.command.user.TopUpBalanceCommand;

public enum CommandManager {
    SHOW_PROFILE(ShowUserProfilePage.INSTANCE),
    SHOW_USER_LOGIN_PAGE(ShowUserLoginPage.INSTANCE),
    SHOW_USER_SIGN_UP_PAGE(ShowUserSignUpPage.INSTANCE),
    SHOW_TARIFFS_PAGE(ShowTariffsPage.INSTANCE),
    SHOW_SETTINGS_PAGE(ShowSettingsPage.INSTANCE),
    SHOW_SUBSCRIPTION_PAGE(ShowSubscriptionPage.INSTANCE),
    SHOW_ADMIN_PAGE(ShowAdminPage.INSTANCE),
    SHOW_SUBSCRIPTION_SETTINGS_PAGE(ShowSubscriptionSettingsPage.INSTANCE),
    SHOW_TARIFF_SETTINGS_PAGE(ShowTariffSettingsPage.INSTANCE),
    SHOW_USERS_FOR_ADMIN_PAGE(ShowUsersForAdminPage.INSTANCE),
    LOGIN(LoginCommand.INSTANCE),
    LOGOUT(LogoutCommand.INSTANCE),
    SIGN_UP(SignUpCommand.INSTANCE),
    SUBSCRIBE(SubscribeCommand.INSTANCE),
    TOP_UP_BALANCE(TopUpBalanceCommand.INSTANCE),
    CHANGE_PASSWORD(ChangePasswordCommand.INSTANCE),
    CONTACT_SUPPORT(ContactSupportCommand.INSTANCE),
    CANCEL_SUBSCRIPTION(CancelSubscriptionCommand.INSTANCE),
    VIEW_DEBTORS(ViewDebtorsCommand.INSTANCE),
    FIND_USER(FindUserCommand.INSTANCE),
    CHANGE_USER_STATUS(ChangeUserStatusCommand.INSTANCE),
    TARIFF_SERVICE(TariffServiceCommand.INSTANCE),
    DELETE_TARIFF(DeleteTariffCommand.INSTANCE),
    FIND_SUBSCRIPTION(FindSubscriptionCommand.INSTANCE),
    VIEW_SUBSCRIPTION_REQUESTS(ViewSubscriptionRequestsCommand.INSTANCE),
    CHANGE_SUBSCRIPTION_STATUS(ChangeSubscriptionStatusCommand.INSTANCE),
    SET_LOCALIZATION(SetLocalizationCommand.INSTANCE),
    DELETE_SUBSCRIPTION(DeleteSubscriptionCommand.INSTANCE),
    DELETE_ACCOUNT(DeleteAccountCommand.INSTANCE),
    DEFAULT(ShowMainPage.INSTANCE);

    private final Command command;

    CommandManager(Command command) {
        this.command = command;
    }

    static Command of(String name) {
        for (CommandManager action : values()) {
            if (action.name().equalsIgnoreCase(name)) {
                return action.command;
            }
        }
        return DEFAULT.command;
    }
}
