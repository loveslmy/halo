package cn.mingyuliu.halo.config.sys;

import cn.mingyuliu.halo.common.entity.Options;
import cn.mingyuliu.halo.common.enums.Option;
import cn.mingyuliu.halo.common.repository.OptionsRepository;
import cn.mingyuliu.halo.service.IOptionService;
import cn.mingyuliu.halo.service.impl.OptionServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


/**
 * <pre>
 *     选项管理组件Test
 * </pre>
 *
 * @author : liumy2009@126.com
 * @date : 2018/09/03
 */
@RunWith(MockitoJUnitRunner.class)
public class OptionHolderTest {

    @InjectMocks
    private IOptionService optionService = new OptionServiceImpl();
    @Mock
    private OptionsRepository optionsRepository;
    private OptionHolder optionHolder;
    private Options dbOptions;
    private Map<String, String> optionParam;
    private List<Options> optionsList;

    @Before
    public void initInstance() {
        optionHolder = new OptionHolder();
        optionHolder.setOptionService(optionService);
        String value = "true";
        dbOptions = new Options();
        dbOptions.setOptionName(Option.IS_INSTALL);
        dbOptions.setOptionValue(value);
        optionParam = Maps.newHashMap();
        optionParam.put(Option.BLOG_URL.name(), "http://mingyuliu.cn");
        optionParam.put(Option.POST_PAGE_COUNT.name(), "10");
        optionsList = Lists.newArrayList();
        optionsList.add(new Options(Option.BLOG_URL,"http://mingyuliu.cn"));
        optionsList.add(new Options(Option.POST_PAGE_COUNT,"10"));
    }

    @Test
    public void testSetAndGet() {
        when(optionsRepository.save(any())).thenReturn(dbOptions);
        dbOptions = optionHolder.set(Option.IS_INSTALL, dbOptions.getOptionValue());
        assertTrue(optionHolder.getBoolean(Option.IS_INSTALL));
    }

    @Test
    public void testLoadOptions() {
        when(optionsRepository.findAll()).thenReturn(optionsList);
        optionHolder.loadOptions();
        Int2ObjectMap<String> options = optionHolder.getOptions();
        assertEquals(2, options.size());
        assertEquals("http://mingyuliu.cn", options.get(Option.BLOG_URL.getCode()));
        assertEquals("10", options.get(Option.POST_PAGE_COUNT.getCode()));
    }

    @Test
    public void testSaveOptions() {
        Map<String, String> options = Maps.newHashMap();
        options.put(Option.BLOG_URL.name(), "http://mingyuliu.cn");
        options.put(Option.POST_PAGE_COUNT.name(), "10");
        optionHolder.saveOptions(options);
        assertEquals(2, optionHolder.getOptions().size());
        assertEquals("http://mingyuliu.cn", optionHolder.get(Option.BLOG_URL));
        assertEquals((byte) 10, optionHolder.getByte(Option.POST_PAGE_COUNT));
    }

    @Test
    public void testUnPersistenceGet() {
        when(optionsRepository.findOptionsByOptionName(any())).thenReturn(dbOptions);
        assertTrue(optionHolder.getBoolean(Option.IS_INSTALL));
    }

    @Test(expected = NumberFormatException.class)
    public void testParseException() {
        when(optionsRepository.findOptionsByOptionName(any())).thenReturn(dbOptions);
        optionHolder.getByte(Option.IS_INSTALL);
    }

    @Test
    public void testEmptyOptionSave() {
        optionHolder.saveOptions(Maps.newHashMap());
        assertTrue(optionHolder.getOptions().isEmpty());
    }

}
