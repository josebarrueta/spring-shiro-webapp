package com.stormpath.sample.impl.service;

import com.stormpath.sample.api.service.AccountService;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.Accounts;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.Applications;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.directory.Directory;
import com.stormpath.sdk.group.Group;
import com.stormpath.sdk.group.GroupMembership;
import com.stormpath.sdk.group.Groups;
import com.stormpath.sdk.lang.Assert;
import com.stormpath.sdk.resource.ResourceException;
import com.stormpath.sdk.tenant.Tenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class DefaultAccountService provides an implementation of the {code}AccountService{code}
 * which communicates with Stormpath APIs to perform account operations.
 *
 * @author josebarrueta
 * @since 1.0
 */
@Service
public class DefaultAccountService implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultAccountService.class);

    private final Client stormpathClient;
    private final String applicationUrl;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public Account getAccount() {
        return applicationContext.getBean("accountRetriever", Account.class);
    }

    @Autowired
    public DefaultAccountService(Client stormpathClient, @Value("${stormpath.application.resturl}") String applicationUrl) {
        Assert.notNull(stormpathClient, "stormpathClient is required.");
        Assert.hasText(applicationUrl, "applicationUrl is required.");
        this.stormpathClient = stormpathClient;
        this.applicationUrl = applicationUrl;
    }


    @Override
    public void createAccount(Account account) {

        Application application = stormpathClient.getResource(applicationUrl, Application.class);

        application.createAccount(account);
    }

    private void deleteDirectory() {

        final Directory directory = stormpathClient.getResource("https://api.stormpath.com/v1/directories/2de3x7DkXhqXH25BaowNtm", Directory.class);

        List<Runnable> runnables = new ArrayList<>();

        for (int i = 0; i < 50; i++) {
            final int count = i;
            Thread runnable = new Thread() {
                @Override
                public void run() {
                    logger.info("deleting: " + count);
                    directory.delete();
                    logger.info("deleted: " + count);
                }
            };

            runnables.add(runnable);
        }

        for (int i = 49; i >= 0; i--) {
            try {
                runnables.get(i);
            } catch (ResourceException e) {
                logger.error("Failed with status code {}, thread id: {}", e.getStatus(), i);
            }
        }
    }

    private void createApplications() {

        Tenant tenant = stormpathClient.getCurrentTenant();

        String applicationNamePrefix = "MyPaginatedApp ";

        for (int i = 0; i < 250; i++) {
            Application application = stormpathClient.instantiate(Application.class);
            application.setName(applicationNamePrefix + i);
            tenant.createApplication(application);


            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        }
    }

    private void createDirectories() {

        Tenant tenant = stormpathClient.getCurrentTenant();

        String directoryNamePrefix = "TenantPaginatedDirs ";

        for (int i = 0; i < 250; i++) {
            Directory directory = stormpathClient.instantiate(Directory.class);
            directory.setName(directoryNamePrefix + intToString(i, 3));
            tenant.createDirectory(directory);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        }
    }

    private void createDirectoryGroups() {

        Directory directory = stormpathClient.getResource("https://staging-api-b.stormpath.com/v1/directories/1g2R0Lv8u2BNNolz4bMeMK", Directory.class);
        String groupNamePrefix = "DirectoryPaginatedGroups ";

        for (int i = 0; i < 250; i++) {
            Group group = stormpathClient.instantiate(Group.class);
            group.setName(groupNamePrefix + intToString(i, 3));
            directory.createGroup(Groups.newCreateRequestFor(group).build());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        }
    }

    private void addMappingWithApplicatons(){

       // stormpathClient.getResource(Applications.name().containsIgnoreCase(""));
    }

    public static String intToString(int num, int digits) {
        assert digits > 0 : "Invalid number of digits";

        // create variable length array of zeros
        char[] zeros = new char[digits];
        Arrays.fill(zeros, '0');
        // format number as String
        DecimalFormat df = new DecimalFormat(String.valueOf(zeros));

        return df.format(num);
    }

    private void createDirectoryAccounts() {

        Directory directory = stormpathClient.getResource("https://staging-api-b.stormpath.com/v1/directories/1g2R0Lv8u2BNNolz4bMeMK", Directory.class);

        Group group = stormpathClient.getResource("https://staging-api-b.stormpath.com/v1/groups/4Z6g95q5tP24AIMBWHLkro", Group.class);

        for (int i = 0; i < 250; i++) {
            Account account = stormpathClient.instantiate(Account.class);
            account.setGivenName("Test" + intToString(i, 3));
            account.setSurname("Acct" + intToString(i, 3));
            account.setEmail("test" + intToString(i, 3) + "@yopmail.com");
            account.setPassword("TestMy" + intToString(i, 4));
            directory.createAccount(Accounts.newCreateRequestFor(account).build());

            GroupMembership membership = group.addAccount(account);

            Assert.notNull(membership.getHref());

            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public List<Account> retrieveAccounts() {

//        createDirectoryGroups();
         createDirectories();
//
//
//        Subject currentUser = SecurityUtils.getSubject();
//
//        String accountHref = currentUser.getPrincipal().toString();
//
//        Account sdkAccount = stormpathClient.getDataStore().getResource(accountHref, Account.class);
//        AccountList accountList = sdkAccount.getDirectory().getAccounts();
//
//        List<Account> accountsToRetrieve;
//
//        if (accountList != null) {
//            accountsToRetrieve = new ArrayList<Account>();
//            for (Account acct : accountList) {
//                Account dfAcct = stormpathClient.getDataStore().instantiate(Account.class);
//                dfAcct.setEmail(acct.getEmail());
//                dfAcct.setGivenName(acct.getGivenName());
//                dfAcct.setUsername(acct.getUsername());
//                dfAcct.setSurname(acct.getSurname());
//                dfAcct.setStatus(dfAcct.getStatus());
//                accountsToRetrieve.add(dfAcct);
//            }
//        } else {
//            accountsToRetrieve = Collections.emptyList();
//        }
        return Collections.emptyList();
    }
}
