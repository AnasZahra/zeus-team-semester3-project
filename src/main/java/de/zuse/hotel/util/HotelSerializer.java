package de.zuse.hotel.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.zuse.hotel.core.Floor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HotelSerializer
{
    ObjectMapper mapper;

    private static final String YAML_WARNING_MESSAGE = "# Do not modify this file manually";

    public HotelSerializer()
    {
        mapper = new ObjectMapper(new YAMLFactory());
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
    }

    // TODO: (Basel) make it with generics!
    public void serializeFloor(Floor floor) throws IOException
    {
        File file = new File("room.yaml");

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        bufferedWriter.write(YAML_WARNING_MESSAGE);

        mapper.writeValue(bufferedWriter, floor);
        bufferedWriter.close();
    }

    public Floor deserializeFloor(String filePath) throws IOException
    {
        File file = new File(filePath);
        if (file.exists() && file.canRead())
        {
            // Deserialize
            Floor floor = mapper.readValue(file, Floor.class);
            ZuseCore.coreAssert(floor != null,"floor is null, Check Serializer!!");

            return floor;
        }

        ZuseCore.coreAssert(false,"can not find or read the file!!");
        return null;
    }

}
