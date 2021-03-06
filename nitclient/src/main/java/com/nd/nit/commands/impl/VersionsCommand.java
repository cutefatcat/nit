package com.nd.nit.commands.impl;

import com.nd.nit.commands.Command;
import com.nd.nit.models.ListVersionModel;
import com.nd.nit.models.VersionModel;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class VersionsCommand extends BaseCommand implements Command{
    @Override
    public void execute() {
        List<VersionModel> versionsList = getVersionsList();
        printVersions(versionsList);
    }

    private List<VersionModel> getVersionsList(){
        String baseUrl;
        try {
            baseUrl = getBaseUrl();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/version";

        ListVersionModel model  = restTemplate.getForObject(url, ListVersionModel.class);
        List<VersionModel> versionsList = model.getListVersions();

        return versionsList;
    }

    private void printVersions(List<VersionModel> versionsList){
        System.out.println("┌──────┬─────────────────────────┬─────────────────────────────────────────────────────────┐");
        System.out.println("| id  |    create date       |                   description                     |");
        System.out.println("├──────┼─────────────────────────┼─────────────────────────────────────────────────────────┤");

        for (VersionModel version : versionsList) {
            if(version.getId() < 10){
                System.out.printf("  %d   |", version.getId());
            } else if (version.getId() < 100){
                System.out.printf("  %d  |", version.getId());
            } else {
                System.out.printf("  %d |", version.getId());
            }

            System.out.print(" " + version.getCreateDate() + "  ");
            System.out.printf("| %s %n", version.getDescription());

            if (versionsList.lastIndexOf(version) == versionsList.size()-1) {
                System.out.println("└──────┴─────────────────────────┴─────────────────────────────────────────────────────────┘");
            } else {
                System.out.println("├──────┼─────────────────────────┼─────────────────────────────────────────────────────────┤");
            }
        }
    }
}
