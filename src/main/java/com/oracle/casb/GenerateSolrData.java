package com.oracle.casb;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

import com.google.common.io.Files;
import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;

public class GenerateSolrData {

    private static Map<Integer, LocalDate> YEAR_FIRST_WMWEEK_DAY = new HashMap<>();
    String FILE_DIRECTORY ="/Users/a0s06ho/github/casb-test/generatefiles/";

    DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T00:00:00Z'").withLocale(Locale.getDefault());
    DateTimeFormatter DAY_FORMATTER = DateTimeFormatter.ofPattern("EEE").withLocale(Locale.getDefault());

    Map<String, Set<String>> FLOW_TABLES_COLUMNS
            = ImmutableMap.<String, Set<String>>builder()
            .put("GDC_IB", ImmutableSet.<String>builder().add("refresh_date").add("target_date").add("ly_actuals_date").add("wm_week").add("region_name").add("volume_type").add("dc_id").add("dc_name").add("chamber_name").add("whse_area").add("department_id").add("department_name").add("category_id").add("category_name").add("item_id").add("item_name").add("capacity_cases").add("ly_actuals_cases").add("forecast_cases").add("adjusted_cases").add("breach_cases").add("adjusted_breach_cases").build())
            .put("GDC_OB", ImmutableSet.<String>builder().add("refresh_date").add("target_date").add("ly_actuals_date").add("wm_week").add("region_name").add("volume_type").add("dc_id").add("dc_name").add("chamber_name").add("whse_area").add("department_id").add("department_name").add("category_id").add("category_name").add("item_id").add("item_name").add("capacity_cases").add("ly_actuals_cases").add("forecast_cases").add("adjusted_cases").add("breach_cases").add("adjusted_breach_cases").build())
            .put("RDC_IB", ImmutableSet.<String>builder().add("refresh_date").add("target_date").add("ly_actuals_date").add("wm_week").add("region_name").add("dc_id").add("dc_name").add("channel_name").add("department_id").add("department_name").add("category_id").add("category_name").add("item_id").add("item_name").add("capacity_cases").add("ly_actuals_cases").add("forecast_cases").add("adjusted_cases").add("breach_cases").add("adjusted_breach_cases").build())
            .put("RDC_OB", ImmutableSet.<String>builder().add("refresh_date").add("target_date").add("ly_actuals_date").add("wm_week").add("region_name").add("dc_id").add("dc_name").add("channel_name").add("pack_type").add("department_id").add("department_name").add("category_id").add("category_name").add("item_id").add("item_name").add("capacity_cases").add("ly_actuals_cases").add("ly_actual_picks").add("forecast_cases").add("forecast_picks").add("adjusted_cases").add("adjusted_picks").add("breach_cases").add("breach_picks").add("adjusted_breach_cases").add("adjusted_breach_picks").build())
            .build();


    Map<String, Set<String>> METRICS_DB_TABLES_COLUMNS
            = ImmutableMap.<String, Set<String>>builder()
            .put("GDC_IB", ImmutableSet.<String>builder().add("refresh_date").add("volume_type").add("dc_id").add("chamber_name").add("whse_area").add("wm_date").add("wm_yr_wk_nbr").add("wm_qtr_nbr").add("wm_full_yr_nbr").add("ly_comp_yr_wk_nbr").add("actual_forecast_flag").add("volume_cases").build())
            .put("GDC_OB", ImmutableSet.<String>builder().add("refresh_date").add("volume_type").add("dc_id").add("chamber_name").add("whse_area").add("wm_date").add("wm_yr_wk_nbr").add("wm_qtr_nbr").add("wm_full_yr_nbr").add("ly_comp_yr_wk_nbr").add("actual_forecast_flag").add("volume_cases").build())
            .put("RDC_IB", ImmutableSet.<String>builder().add("refresh_date").add("dc_id").add("channel_name").add("wm_date").add("wm_yr_wk_nbr").add("wm_qtr_nbr").add("wm_full_yr_nbr").add("ly_comp_yr_wk_nbr").add("actual_forecast_flag").add("volume_cases").build())
            .put("RDC_OB", ImmutableSet.<String>builder().add("refresh_date").add("dc_id").add("channel_name").add("pack_type").add("wm_date").add("wm_yr_wk_nbr").add("wm_qtr_nbr").add("wm_full_yr_nbr").add("ly_comp_yr_wk_nbr").add("actual_forecast_flag").add("volume_cases").add("volume_picks").build())
            .build();

    Map<String, Set<String>> METRICS_YOY_TABLES_COLUMNS
            = ImmutableMap.<String, Set<String>>builder()
            .put("GDC_IB", ImmutableSet.<String>builder().add("refresh_date").add("volume_type").add("dc_id").add("chamber_name").add("whse_area").add("wm_yr_wk_nbr").add("cases_ty").add("cases_ly").add("cases_lw").add("capacity_cases").build())
            .put("GDC_OB", ImmutableSet.<String>builder().add("refresh_date").add("volume_type").add("dc_id").add("chamber_name").add("whse_area").add("wm_yr_wk_nbr").add("cases_ty").add("cases_ly").add("cases_lw").add("capacity_cases").build())
            .put("RDC_IB", ImmutableSet.<String>builder().add("refresh_date").add("dc_id").add("channel_name").add("wm_yr_wk_nbr").add("cases_ty").add("cases_ly").add("cases_lw").add("capacity_cases").build())
            .put("RDC_OB", ImmutableSet.<String>builder().add("refresh_date").add("dc_id").add("channel_name").add("pack_type").add("wm_yr_wk_nbr").add("cases_ty").add("picks_ty").add("cases_ly").add("picks_ly").add("cases_lw").add("picks_lw").add("capacity_cases").build())
            .build();

    Map<String, Set<String>> ACCURACY_TABLES_COLUMNS
            = ImmutableMap.<String, Set<String>>builder()
            .put("GDC_IB", ImmutableSet.<String>builder().add("refresh_date").add("target_date").add("week_day").add("wm_week").add("region_name").add("volume_type").add("dc_id").add("dc_name").add("chamber_name").add("whse_area").add("department_id").add("department_name").add("category_id").add("category_name").add("actual_cases").add("system_cases").add("adjusted_cases").add("days_out").add("system_accuracy").add("adjusted_accuracy").build())
            .put("GDC_OB", ImmutableSet.<String>builder().add("refresh_date").add("target_date").add("week_day").add("wm_week").add("region_name").add("volume_type").add("dc_id").add("dc_name").add("chamber_name").add("whse_area").add("department_id").add("department_name").add("category_id").add("category_name").add("actual_cases").add("system_cases").add("adjusted_cases").add("days_out").add("system_accuracy").add("adjusted_accuracy").build())
            .put("RDC_IB", ImmutableSet.<String>builder().add("refresh_date").add("target_date").add("week_day").add("wm_week").add("region_name").add("dc_id").add("dc_name").add("channel_name").add("department_id").add("department_name").add("category_id").add("category_name").add("actual_cases").add("system_cases").add("adjusted_cases").add("days_out").add("system_accuracy").add("adjusted_accuracy").build())
            .put("RDC_OB", ImmutableSet.<String>builder().add("refresh_date").add("target_date").add("week_day").add("wm_week").add("region_name").add("dc_id").add("dc_name").add("channel_name").add("pack_type").add("department_id").add("department_name").add("category_id").add("category_name").add("actual_cases").add("actual_picks").add("system_cases").add("system_picks").add("adjusted_cases").add("adjusted_picks").add("days_out").add("system_accuracy").add("adjusted_accuracy").build())
            .build();

    Map<String, List<Integer>> DC_LIST
            = ImmutableMap.<String, List<Integer>>builder()
            .put("GDC", ImmutableList.of(6082, 6083, 7016))
            .put("RDC", ImmutableList.of(6080, 6016, 7045))
            .build();
    Map<String, List<String>> CHAMBERS
            = ImmutableMap.<String, List<String>>builder()
            .put("GDC", ImmutableList.of("DRY","MP","DAIRY-DALI"))
            .put("RDC", ImmutableList.of("DISTRIBUTION","STAPLE"))
            .build();

    Map<String, List<String>> CHAMBER_CHANNEL_AREAS
            = ImmutableMap.<String, List<String>>builder()
            .put("DRY", ImmutableList.of("DRY"))
            .put("MP", ImmutableList.of("MEAT","PRODUCE"))
            .put("DAIRY-DALI", ImmutableList.of("DAIRY","DAIRY-DALI"))
            .put("DISTRIBUTION", ImmutableList.of("CONVEYABLE","NON-CONVEYABLE","BREACKPACK"))
            .put("STAPLE", ImmutableList.of("CONVEYABLE","NON-CONVEYABLE","BREACKPACK"))
            .build();

    private Map<Integer, String> DC_NAME_MAP
            = ImmutableMap.<Integer, String>builder()
            .put(6082,"CLARKSVILLE, AR GDC")
            .put(6083,"TEMPLE, TX GDC")
            .put(7016,"GORDONSVILLE, VA GDC")
            .put(6080,"TOBYHANNA, PA RDC")
            .put(6016,"NEW BRAUNFELS, TX RDC")
            .put(7045,"MT CRAWFORD, VA RDC")
            .build();


    public static void main(String[] args) {
        GenerateSolrData genData = new GenerateSolrData();
        //genData.parseFile();
        try {
            genData.generateMetricsData();
            genData.generateAccuracyData();
            genData.generateMasterData();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }



    private void parseFile() {
        try {
            Any any = parseDcDetails(6082);
            System.out.printf("DC ID - %d : DC_NAME - %s\n", any.get("dc_id").toInt(), any.get("dc_name").toString());
            List<Any> chambers =  any.get("chamber_channel_list").asList();
            for(Any chamber : chambers) {
                String chamberName = chamber.get("chamber_channel_name").toString();
                System.out.printf("Chamber Name %s\n", chamberName);
                List<Any> whAreas = chamber.get("whse_area_pack_type_list").asList();
                for (Any whArea : whAreas) {
                    String whAreaName = whArea.get("whse_area_pack_type").toString();
                    System.out.printf("WH ARea name %s\n", whAreaName);
                    List<Any> departmentList = whArea.get("departments").asList();
                    for(Any department : departmentList) {
                        System.out.printf("Department id : [%d], Department Name [%s]\n", department.get("department_id").toInt(), department.get("department_name").toString());
                        List<Any> categories = department.get("categories").asList();
                        for(Any category : categories) {
                            System.out.printf("Category id : [%d], Category Name [%s]\n", category.get("category_id").toInt(), category.get("category_name").toString());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateMetricsData() throws ParseException, IOException {
        for(Map.Entry<String,Set<String>> tableEntry: METRICS_DB_TABLES_COLUMNS.entrySet()) {
            generateTable(tableEntry,"dashboard");
        }
        for(Map.Entry<String,Set<String>> tableEntry: METRICS_YOY_TABLES_COLUMNS.entrySet()) {
            generateTable(tableEntry,"growth");
        }
    }

    private void generateAccuracyData() throws IOException, ParseException {
        for(Map.Entry<String,Set<String>> tableEntry: ACCURACY_TABLES_COLUMNS.entrySet()) {
            generateAccuracyTable(tableEntry,"accuracy");
        }
    }
    private void generateMasterData() throws IOException, ParseException {
        for(Map.Entry<String,Set<String>> tableEntry: FLOW_TABLES_COLUMNS.entrySet()) {
            generateMasterTable(tableEntry,"master");
        }
    }



    private void generateTable(Map.Entry<String, Set<String>> tableEntry, String tableType) throws ParseException, IOException {
        Set<String> columns = tableEntry.getValue();
        String tableName = tableEntry.getKey();
        String[] splitted = tableName.split("_");
        String network = splitted[0];
        String flow = splitted[1];
        String fileName = FILE_DIRECTORY.concat(tableName.toLowerCase()).concat("_").concat(tableType.toLowerCase()).concat(".csv");
        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        CSVPrinter csvPrinter = new CSVPrinter(printWriter, CSVFormat.DEFAULT.withHeader(columns.toArray(new String[columns.size()])));
        LocalDate start_date =  LocalDate.parse("2020-04-01");
        LocalDate end_date =  LocalDate.parse("2020-05-31");
        for(LocalDate wmDate = start_date; wmDate.compareTo(end_date) < 0; wmDate = wmDate.plusDays(1)) {
            for (Integer dcId : DC_LIST.get(network)) {
                for (String chamber : CHAMBERS.get(network)) {
                    for (String whArea : CHAMBER_CHANNEL_AREAS.get(chamber.toUpperCase())) {
                        csvPrinter.printRecord(columnValues(columns, wmDate, dcId, chamber, whArea));
                    }
                }

            }
        }
        csvPrinter.flush();
    }

    private void generateMasterTable(Map.Entry<String, Set<String>> tableEntry, String tableType) throws ParseException, IOException {
        Set<String> columns = tableEntry.getValue();
        String tableName = tableEntry.getKey();
        String[] splitted = tableName.split("_");
        String network = splitted[0];
        String flow = splitted[1];
        String fileName = FILE_DIRECTORY.concat(tableName.toLowerCase()).concat("_").concat(tableType.toLowerCase()).concat(".csv");
        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        CSVPrinter csvPrinter = new CSVPrinter(printWriter, CSVFormat.DEFAULT.withHeader(columns.toArray(new String[columns.size()])));
        LocalDate start_date =  LocalDate.parse("2020-03-01");
        LocalDate end_date =  LocalDate.parse("2020-05-20");
        for(LocalDate wmDate = start_date; wmDate.compareTo(end_date) < 0; wmDate = wmDate.plusDays(1)) {
            for (Integer dcId : DC_LIST.get(network)) {
                Any dcLevel = parseDcDetails(dcId);
                List<Any> chambers =  dcLevel.get("chamber_channel_list").asList();
                for(Any chamberLevel : chambers) {
                    List<Any> whAreas = chamberLevel.get("whse_area_pack_type_list").asList();
                    for(Any areaLevel : whAreas) {
                        for(int daysOut = 1; daysOut <= 91; daysOut++) {
                            csvPrinter.printRecord(createMasterRow(columns, wmDate, dcLevel, chamberLevel, areaLevel, daysOut));
                        }
                    }
                }
            }
        }
        csvPrinter.flush();
    }

    private void generateAccuracyTable(Map.Entry<String, Set<String>> tableEntry, String tableType) throws ParseException, IOException {
        Set<String> columns = tableEntry.getValue();
        String tableName = tableEntry.getKey();
        String[] splitted = tableName.split("_");
        String network = splitted[0];
        String flow = splitted[1];
        String fileName = FILE_DIRECTORY.concat(tableName.toLowerCase()).concat("_").concat(tableType.toLowerCase()).concat(".csv");
        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        CSVPrinter csvPrinter = new CSVPrinter(printWriter, CSVFormat.DEFAULT.withHeader(columns.toArray(new String[columns.size()])));
        LocalDate start_date =  LocalDate.parse("2020-01-01");
        LocalDate end_date =  LocalDate.parse("2020-05-10");
        for(LocalDate wmDate = start_date; wmDate.compareTo(end_date) < 0; wmDate = wmDate.plusDays(1)) {
            for (Integer dcId : DC_LIST.get(network)) {
                Any dcLevel = parseDcDetails(dcId);
                List<Any> chambers =  dcLevel.get("chamber_channel_list").asList();
                for(Any chamberLevel : chambers) {
                    List<Any> whAreas = chamberLevel.get("whse_area_pack_type_list").asList();
                    for(Any areaLevel : whAreas) {
                        for(int daysOut = 1; daysOut <= 91; daysOut++) {
                            csvPrinter.printRecord(createAccuracyRow(columns, wmDate, dcLevel, chamberLevel, areaLevel, daysOut));
                        }
                    }
                }
            }
        }
        csvPrinter.flush();
    }

    private Iterable<?> createMasterRow(Set<String> columns, LocalDate wmDate, Any dcLevel,
                                          Any chamberLevel, Any areaLevel, int daysOut) {
        LocalDate current_date = LocalDate.now();
        LocalDate refresh_date =  LocalDate.parse("2020-05-25");
        ImmutableList.Builder builder = ImmutableList.builder();
        for (String columnName : columns) {
            if("refresh_date".equalsIgnoreCase(columnName)) {
                builder.add(DATE_FORMATTER.format(refresh_date));
            } else if("target_date".equalsIgnoreCase(columnName)) {
                builder.add(DATE_FORMATTER.format(wmDate));
            } else if("ly_actuals_date".equalsIgnoreCase(columnName)) {
                builder.add(DATE_FORMATTER.format(wmDate.plusYears(-1)));
            } else if("wm_week".equalsIgnoreCase(columnName)) {
                builder.add( getWmWeekNumFromDate(wmDate));
            } else if("region_name".equalsIgnoreCase(columnName)) {
                builder.add("CENTRAL");
            } else if("volume_type".equalsIgnoreCase(columnName)) {
                builder.add(18);
            } else if("dc_id".equalsIgnoreCase(columnName)) {
                builder.add(dcLevel.get("dc_id").toInt());
            } else if("dc_name".equalsIgnoreCase(columnName)) {
                builder.add(dcLevel.get("dc_name").toString());
            } else if("chamber_name".equalsIgnoreCase(columnName)
                    ||"channel_name".equalsIgnoreCase(columnName)) {
                builder.add(chamberLevel.get("chamber_channel_name").toString());
            } else if("whse_area".equalsIgnoreCase(columnName)
                    ||"pack_type".equalsIgnoreCase(columnName)) {
                builder.add(areaLevel.get("whse_area_pack_type").toString());
            } else if("department_id".equalsIgnoreCase(columnName)
                    ||"category_id".equalsIgnoreCase(columnName)
                    ||"item_id".equalsIgnoreCase(columnName)) {
                builder.add(-1);
            } else if("department_name".equalsIgnoreCase(columnName)
                    ||"category_name".equalsIgnoreCase(columnName)
                    ||"item_name".equalsIgnoreCase(columnName)) {
                builder.add("");
            } else if("actual_cases".equalsIgnoreCase(columnName)
                    ||"system_cases".equalsIgnoreCase(columnName)
                    ||"adjusted_cases".equalsIgnoreCase(columnName)
                    ||"actual_picks".equalsIgnoreCase(columnName)
                    ||"system_picks".equalsIgnoreCase(columnName)
                    ||"adjusted_picks".equalsIgnoreCase(columnName)
                    ||"ly_actuals_cases".equalsIgnoreCase(columnName)
                    ||"ly_actual_picks".equalsIgnoreCase(columnName)
                    ||"forecast_cases".equalsIgnoreCase(columnName)
                    ||"forecast_picks".equalsIgnoreCase(columnName)
                    ||"adjusted_cases".equalsIgnoreCase(columnName)
                    ||"adjusted_picks".equalsIgnoreCase(columnName)
                    ||"breach_cases".equalsIgnoreCase(columnName)
                    ||"breach_picks".equalsIgnoreCase(columnName)
                    ||"adjusted_breach_cases".equalsIgnoreCase(columnName)
                    ||"adjusted_breach_picks".equalsIgnoreCase(columnName)
            ) {
                builder.add(generateRamndomVolume());
            } else if ("capacity_cases".equalsIgnoreCase(columnName)) {
                builder.add(generateRamndomVolume(35000d, 45000d));
            }
        }
        return builder.build();
    }
    private Iterable<?> createAccuracyRow(Set<String> columns, LocalDate wmDate, Any dcLevel,
                                          Any chamberLevel, Any areaLevel, int daysOut) {
        LocalDate current_date = LocalDate.now();
        LocalDate refresh_date =  LocalDate.parse("2020-05-07");
        ImmutableList.Builder builder = ImmutableList.builder();
        for (String columnName : columns) {
            if("refresh_date".equalsIgnoreCase(columnName)) {
                builder.add(DATE_FORMATTER.format(refresh_date));
            } else if("target_date".equalsIgnoreCase(columnName)) {
                builder.add(DATE_FORMATTER.format(wmDate));
            } else if("week_day".equalsIgnoreCase(columnName)) {
                builder.add(DAY_FORMATTER.format(wmDate));
            } else if("wm_week".equalsIgnoreCase(columnName)) {
                builder.add( getWmWeekNumFromDate(wmDate));
            } else if("region_name".equalsIgnoreCase(columnName)) {
                builder.add("CENTRAL");
            } else if("volume_type".equalsIgnoreCase(columnName)) {
                builder.add(-1);
            } else if("dc_id".equalsIgnoreCase(columnName)) {
                builder.add(dcLevel.get("dc_id").toInt());
            } else if("dc_name".equalsIgnoreCase(columnName)) {
                builder.add(dcLevel.get("dc_name").toString());
            } else if("chamber_name".equalsIgnoreCase(columnName)
            ||"channel_name".equalsIgnoreCase(columnName)) {
                builder.add(chamberLevel.get("chamber_channel_name").toString());
            } else if("whse_area".equalsIgnoreCase(columnName)
            ||"pack_type".equalsIgnoreCase(columnName)) {
                builder.add(areaLevel.get("whse_area_pack_type").toString());
            } else if("department_id".equalsIgnoreCase(columnName) ||"category_id".equalsIgnoreCase(columnName)) {
                builder.add(-1);
            } else if("department_name".equalsIgnoreCase(columnName) ||"category_name".equalsIgnoreCase(columnName)) {
                builder.add("");
            } else if("actual_cases".equalsIgnoreCase(columnName)
             ||"system_cases".equalsIgnoreCase(columnName)
             ||"adjusted_cases".equalsIgnoreCase(columnName)
             ||"actual_picks".equalsIgnoreCase(columnName)
             ||"system_picks".equalsIgnoreCase(columnName)
             ||"adjusted_picks".equalsIgnoreCase(columnName)
           ) {
                builder.add(generateRamndomVolume());
            } else if ("days_out".equalsIgnoreCase(columnName)) {
                builder.add(daysOut);
            } else if("system_accuracy".equalsIgnoreCase(columnName)
            ||"adjusted_accuracy".equalsIgnoreCase(columnName)) {
                builder.add(generateRamndomVolume(0.6d, 0.95d));
            }
        }
        return builder.build();
    }

    private Iterable<?> columnValues( Set<String> columns, LocalDate wmDate, Integer dcId,
                                     String chamberChannel, String whAreaPackType) {
        LocalDate current_date = LocalDate.now();
        LocalDate refresh_date =  LocalDate.parse("2020-05-07");
        ImmutableList.Builder builder = ImmutableList.builder();
        for (String columnName : columns) {
            if("refresh_date".equalsIgnoreCase(columnName)) {
                builder.add(DATE_FORMATTER.format(refresh_date));
            } else if("volume_type".equalsIgnoreCase(columnName)) {
                builder.add( -1);
            } else if("dc_id".equalsIgnoreCase(columnName)) {
                builder.add( dcId);
            } else if("chamber_name".equalsIgnoreCase(columnName)
                    ||"channel_name".equalsIgnoreCase(columnName)) {
                builder.add(chamberChannel);
            } else if("whse_area".equalsIgnoreCase(columnName)
             ||"pack_type".equalsIgnoreCase(columnName)) {
                builder.add(whAreaPackType);
            } else if ("wm_date".equalsIgnoreCase(columnName)) {
                builder.add( DATE_FORMATTER.format(wmDate));
            } else if("wm_yr_wk_nbr".equalsIgnoreCase(columnName)) {
                builder.add( getWmWeekNumFromDate(wmDate));
            } else if("wm_qtr_nbr".equalsIgnoreCase(columnName)) {
                builder.add( getQuarterNumber(wmDate.getMonth()));
            } else if("wm_full_yr_nbr".equalsIgnoreCase(columnName)) {
                builder.add( wmDate.getYear());
            } else if ("ly_comp_yr_wk_nbr".equalsIgnoreCase(columnName)) {
                builder.add( getWmWeekNumFromDate(wmDate.plusYears(-1)));
            } else if ("actual_forecast_flag".equalsIgnoreCase(columnName)) {
                builder.add( wmDate.compareTo(current_date) <= 0 ? 'A' : 'F');
            } else if ("volume_cases".equalsIgnoreCase(columnName)) {
                builder.add( generateRamndomVolume());
            } else if ("volume_cases".equalsIgnoreCase(columnName)) {
                builder.add( generateRamndomVolume());
            } else if ("volume_picks".equalsIgnoreCase(columnName)) {
                builder.add(generateRamndomVolume());
            } else if("cases_ty".equalsIgnoreCase(columnName)
            ||"cases_ly".equalsIgnoreCase(columnName)
            ||"cases_lw".equalsIgnoreCase(columnName)
            ||"picks_ty".equalsIgnoreCase(columnName)
            ||"picks_ly".equalsIgnoreCase(columnName)
            ||"picks_lw".equalsIgnoreCase(columnName)){
                builder.add( generateRamndomVolume());
            } else if("cases_ly".equalsIgnoreCase(columnName)) {
                builder.add( generateRamndomVolume());
            } else if("capacity_cases".equalsIgnoreCase(columnName)) {
                builder.add( generateRamndomVolume());
            } else {
                System.out.println("Invalid column name :" + columnName);
            }
        }
        return builder.build();
    }

    private Double generateRamndomVolume(Double rangeMin, Double rangeMax) {
        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        return randomValue;
    }
    private Double generateRamndomVolume() {
        Random r = new Random();
        Double rangeMin = 2134d;
        Double rangeMax = 3926d;
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        return randomValue;
    }

    private Integer getQuarterNumber(Month month) {
        if(month == Month.FEBRUARY || month == Month.MARCH || month == Month.APRIL) {
            return 1;
        } else if(month == Month.MAY || month == Month.JUNE || month == Month.JULY) {
            return 2;
        } else if(month == Month.AUGUST || month == Month.SEPTEMBER || month == Month.OCTOBER) {
            return 3;
        }
        return 4;
    }

    private Any parseDcDetails(Integer dcId) throws IOException {
        Any any =   JsonIterator.deserialize(readFile(dcId));
        //System.out.println(any.get("dc_name"));
        return any;
    }

    private byte[] readFile(Integer dcId) throws IOException {
        String fileName ="dc_".concat(String.valueOf(dcId)).concat(".json");
        File initialFile = new File("/Users/a0s06ho/github/casb-test/src/main/resources/dc_details/".concat(fileName));
        return IOUtils.toByteArray(Files.asByteSource(initialFile).openStream());
    }

    private String getDcName(Integer dcId) {
        return DC_NAME_MAP.get(dcId);
    }

    ImmutableMap<Integer, List<String>> DC_CHAMBER_MAP
            = ImmutableMap.<Integer, List<String>>builder()
            .put(6082, ImmutableList.of("MP","DRY"))
            .build();
    private List<String> getChamberChannels(Integer dcId) {
        return DC_CHAMBER_MAP.get(dcId);
    }


    private static int getWmWeekNumFromDate(LocalDate targetDate) {
        int targetYear = targetDate.getYear();
        LocalDate firstWmWeekDayForYear
                = YEAR_FIRST_WMWEEK_DAY.computeIfAbsent(targetYear,
                k -> getFirstWmWeekDayForYear(targetYear));
        if (targetDate.compareTo(firstWmWeekDayForYear) < 0) {
            int prevYear = targetYear - 1;
            firstWmWeekDayForYear
                    = YEAR_FIRST_WMWEEK_DAY.computeIfAbsent(prevYear,
                    k -> getFirstWmWeekDayForYear(prevYear));
        }
        int noOfDaysBetween = Long.valueOf(ChronoUnit.DAYS.between(firstWmWeekDayForYear, targetDate)).intValue();
        int wmWeekNum = (noOfDaysBetween/7) + 1;
        return firstWmWeekDayForYear.getYear() * 100 + wmWeekNum;
    }

    private static LocalDate getFirstWmWeekDayForYear(int targetYear) {
        LocalDate firstJan = LocalDate.of(targetYear, Month.JANUARY, 1);
        LocalDate lastSatInJan = firstJan.with(TemporalAdjusters.lastInMonth(DayOfWeek.SATURDAY));
        /**
         * If last Saturday falls on 25, Entire week is wrapped in January Only
         * First day of Walmart first week is Feb - 1
         */
        if (lastSatInJan.getDayOfYear() == 25) {
            lastSatInJan = LocalDate.of(targetYear, Month.FEBRUARY, 1);
        }
        return lastSatInJan;
    }

}
